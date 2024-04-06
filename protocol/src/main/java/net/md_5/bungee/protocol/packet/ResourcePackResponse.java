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
import net.md_5.bungee.protocol.ProtocolConstants;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ResourcePackResponse extends DefinedPacket
{

    private Optional<UUID> uuid;
    private int result;

    @Override
    public void read(ByteBuf buf)
    {
        this.result = readVarInt( buf );
    }

    @Override
    public void read(ByteBuf buf, ProtocolConstants.Direction direction, int protocolVersion)
    {
        if ( protocolVersion >= ProtocolConstants.MINECRAFT_1_20_3 )
        {
            this.uuid = Optional.of( readUUID( buf ) );
        }
        read( buf );
    }

    @Override
    public void write(ByteBuf buf)
    {
        writeVarInt( this.result, buf );
    }

    @Override
    public void write(ByteBuf buf, ProtocolConstants.Direction direction, int protocolVersion)
    {
        if ( protocolVersion >= ProtocolConstants.MINECRAFT_1_20_3 && this.uuid.isPresent() )
        {
            writeUUID( this.uuid.get(), buf );
        }
        write( buf );
    }

    @Override
    public void handle(AbstractPacketHandler handler) throws Exception
    {
        handler.handle( this );
    }
}
