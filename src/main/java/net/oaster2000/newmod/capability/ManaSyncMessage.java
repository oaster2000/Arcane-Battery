package net.oaster2000.newmod.capability;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ManaSyncMessage implements IMessage
{
	public ManaSyncMessage(){}

	private float maxMana;

	public ManaSyncMessage(float mana)
	{
		this.maxMana = mana;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		maxMana = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeFloat(maxMana);
	}

	public static class Handler implements IMessageHandler<ManaSyncMessage, IMessage>
	{

		@Override
		public IMessage onMessage(ManaSyncMessage message, MessageContext ctx)
		{
			Minecraft.getMinecraft().addScheduledTask(() ->
			{
				EntityPlayerSP cp = Minecraft.getMinecraft().player;
				if(cp.hasCapability(ManaProvider.MANA_CAP, null))
				{
					IMana caster = cp.getCapability(ManaProvider.MANA_CAP, null);
					caster.set(message.maxMana);
				}
			});
			return null;
		}
	}
}