package com.eventacs.server;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * <p>
 *
 * <dl>
 * <dt><b>Examples:</b></dt>
 * <p>
 * <pre>
 *
 * </pre>
 *
 * <p><dt><b>Immutability:</b></dt>
 * <dd>
 * 	<b>IMMUTABLE</b> and <b>MUTABLE</b>
 * </dd>
 *
 * <p><dt><b>Thread Safety:</b></dt>
 * <dd>
 * 	<b>NOT-THREAD-SAFE</b> and <b>NOT-APPLICABLE</b> (for it will never be used on multi-thread occasion.)
 * </dd>
 *
 * <p><dt><b>Serialization:</b></dt>
 * <dd>
 * 	<b>NOT-SERIALIIZABLE</b> and <b>NOT-APPLICABLE</b> (for it have no need to be serializable.)
 * </dd>
 *
 * <p><dt><b>Design Patterns:</b></dt>
 * <dd>
 *
 * </dd>
 *
 * <p><dt><b>Change History:</b></dt>
 * <dd>
 * 	Date		Author		Action
 * </dd>
 * <dd>
 * 	2012-8-14	henryleu	Create the class
 * </dd>
 *
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class LongRedisSerializer implements RedisSerializer<Long> {

    @Override
    public byte[] serialize(Long t) throws SerializationException {
        byte[] writeBuffer = new byte[ 8 ];
        long v = 0L;
        if(t!=null) {
            v = t.longValue();
        }

        writeBuffer[0] = (byte)(v >>> 56);
        writeBuffer[1] = (byte)(v >>> 48);
        writeBuffer[2] = (byte)(v >>> 40);
        writeBuffer[3] = (byte)(v >>> 32);
        writeBuffer[4] = (byte)(v >>> 24);
        writeBuffer[5] = (byte)(v >>> 16);
        writeBuffer[6] = (byte)(v >>>  8);
        writeBuffer[7] = (byte)v;

        return writeBuffer;

    }

    @Override
    public Long deserialize(byte[] bytes) throws SerializationException {
        long v = (bytes == null ? 0L : ((long)(bytes[0]   & 0xff) << 56) |
                ((long)(bytes[1] & 0xff) << 48) |
                ((long)(bytes[2] & 0xff) << 40) |
                ((long)(bytes[3] & 0xff) << 32) |
                ((long)(bytes[4] & 0xff) << 24) |
                ((long)(bytes[5] & 0xff) << 16) |
                ((long)(bytes[6] & 0xff) << 8) |
                ((long)(bytes[7] & 0xff)) );
        return v;
    }

}
