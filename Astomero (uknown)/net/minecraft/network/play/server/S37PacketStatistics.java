package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import com.google.common.collect.*;
import net.minecraft.stats.*;
import java.io.*;
import java.util.*;
import net.minecraft.network.*;

public class S37PacketStatistics implements Packet<INetHandlerPlayClient>
{
    private Map<StatBase, Integer> field_148976_a;
    
    public S37PacketStatistics() {
    }
    
    public S37PacketStatistics(final Map<StatBase, Integer> p_i45173_1_) {
        this.field_148976_a = p_i45173_1_;
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient handler) {
        handler.handleStatistics(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer buf) throws IOException {
        final int i = buf.readVarIntFromBuffer();
        this.field_148976_a = (Map<StatBase, Integer>)Maps.newHashMap();
        for (int j = 0; j < i; ++j) {
            final StatBase statbase = StatList.getOneShotStat(buf.readStringFromBuffer(32767));
            final int k = buf.readVarIntFromBuffer();
            if (statbase != null) {
                this.field_148976_a.put(statbase, k);
            }
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer buf) throws IOException {
        buf.writeVarIntToBuffer(this.field_148976_a.size());
        for (final Map.Entry<StatBase, Integer> entry : this.field_148976_a.entrySet()) {
            buf.writeString(entry.getKey().statId);
            buf.writeVarIntToBuffer(entry.getValue());
        }
    }
    
    public Map<StatBase, Integer> func_148974_c() {
        return this.field_148976_a;
    }
}
