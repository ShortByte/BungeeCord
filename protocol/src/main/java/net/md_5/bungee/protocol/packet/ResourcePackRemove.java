package net.md_5.bungee.protocol.packet;

import io.netty.buffer.ByteBuf;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.md_5.bungee.protocol.AbstractPacketHandler;
import net.md_5.bungee.protocol.DefinedPacket;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ResourcePackRemove extends DefinedPacket
{

    private Optional<UUID> uuid = Optional.empty();

    @Override
    public void read(ByteBuf buf)
    {
        boolean hasUuid = buf.readBoolean();

        if ( hasUuid )
        {
            this.uuid = Optional.of( readUUID( buf ) );
        }
    }

    @Override
    public void write(ByteBuf buf)
    {
        buf.writeBoolean( this.uuid.isPresent() );

        if ( this.uuid.isPresent() )
        {
            writeUUID( this.uuid.get(), buf );
        }
    }

    @Override
    public void handle(AbstractPacketHandler handler) throws Exception
    {
        handler.handle( this );
    }
}
