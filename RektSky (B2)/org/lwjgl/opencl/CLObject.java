package org.lwjgl.opencl;

import org.lwjgl.*;

abstract class CLObject extends PointerWrapperAbstract
{
    protected CLObject(final long pointer) {
        super(pointer);
    }
    
    final long getPointerUnsafe() {
        return this.pointer;
    }
}
