package net.md_5.bungee.protocol.packet;

import io.netty.buffer.ByteBuf;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.protocol.AbstractPacketHandler;
import net.md_5.bungee.protocol.DefinedPacket;
import net.md_5.bungee.protocol.ProtocolConstants;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ResourcePack extends DefinedPacket
{

    private Optional<UUID> uuid;
    private String url;
    private String hash;
    private boolean forced;
    private Optional<BaseComponent[]> promptMessage = Optional.empty();

    @Override
    public void read(ByteBuf buf)
    {
        this.url = readString( buf );
        this.hash = readString( buf );
        this.forced = buf.readBoolean();
    }

    @Override
    public void read(ByteBuf buf, ProtocolConstants.Direction direction, int protocolVersion)
    {
        if ( protocolVersion >= ProtocolConstants.MINECRAFT_1_20_3 )
        {
            this.uuid = Optional.of( readUUID( buf ) );
        }
        read( buf );

        boolean hasPromptMessage = buf.readBoolean();

        if ( hasPromptMessage )
        {
            this.promptMessage = Optional.of( new BaseComponent[] {readBaseComponent( buf, protocolVersion )} );
        }
    }

    @Override
    public void write(ByteBuf buf)
    {
        writeString( this.url, buf );
        writeString( this.hash, buf );
        buf.writeBoolean( this.forced );
    }

    @Override
    public void write(ByteBuf buf, ProtocolConstants.Direction direction, int protocolVersion)
    {
        if ( protocolVersion >= ProtocolConstants.MINECRAFT_1_20_3 && this.uuid.isPresent() )
        {
            writeUUID( this.uuid.get(), buf );
        }

        write( buf );

        buf.writeBoolean( this.promptMessage.isPresent() );

        if ( this.promptMessage.isPresent() )
        {
            writeBaseComponent( TextComponent.fromArray( this.promptMessage.get() ), buf, protocolVersion );
        }
    }

    @Override
    public void handle(AbstractPacketHandler handler) throws Exception
    {
        handler.handle( this );
    }
}
