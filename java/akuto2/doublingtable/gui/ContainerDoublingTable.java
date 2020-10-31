package akuto2.doublingtable.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerDoublingTable extends ContainerWorkbench{
	private World worldObj;
	private BlockPos pos;
	private int magnification;

	public ContainerDoublingTable(InventoryPlayer inventoryPlayer, World world, BlockPos pos, int type) {
		super(inventoryPlayer, world, pos);

		this.worldObj = world;
		this.pos = pos;
		magnification = type;
	}

	@Override
	protected void slotChangedCraftingGrid(World world, EntityPlayer player, InventoryCrafting craftMatrix, InventoryCraftResult craftResult) {
		if(!world.isRemote) {
			EntityPlayerMP playerMP = (EntityPlayerMP)player;
			ItemStack stack = ItemStack.EMPTY;
			IRecipe iRecipe = CraftingManager.findMatchingRecipe(craftMatrix, world);

			if(iRecipe != null && (iRecipe.isDynamic() || !world.getGameRules().getBoolean("doLimitedCrafting") || playerMP.getRecipeBook().isUnlocked(iRecipe))) {
				craftResult.setRecipeUsed(iRecipe);
				stack = iRecipe.getCraftingResult(craftMatrix);
				stack.setCount(stack.getCount() * magnification);
			}

			craftResult.setInventorySlotContents(0, stack);
			playerMP.connection.sendPacket(new SPacketSetSlot(windowId, 0, stack));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getDistanceSq(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F) <= 64.0D;
	}
}
