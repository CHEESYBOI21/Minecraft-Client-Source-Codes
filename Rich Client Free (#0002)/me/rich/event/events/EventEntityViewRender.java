package me.rich.event.events;
import me.rich.event.Event;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;

public abstract class EventEntityViewRender extends Event {

    private final EntityRenderer renderer;
    private final Entity entity;
    private final IBlockState state;
    private final double renderPartialTicks;

    public EventEntityViewRender(EntityRenderer renderer, Entity entity, IBlockState state, double renderPartialTicks)
    {
        this.renderer = renderer;
        this.entity = entity;
        this.state = state;
        this.renderPartialTicks = renderPartialTicks;
    }

    public EntityRenderer getRenderer()
    {
	    return renderer;
	}

    public Entity getEntity()
    {
        return entity;
    }

    public IBlockState getState()
    {
        return state;
    }

    public double getRenderPartialTicks()
    {
        return renderPartialTicks;
    }

	/**
     * Event that allows any feature to customize the fog density the player sees.
     * NOTE: In order to make this event have an effect, you must cancel the event
     */

    public static class FogDensity extends EventEntityViewRender
    {
        private float density;

        public FogDensity(EntityRenderer renderer, Entity entity, IBlockState state, double renderPartialTicks, float density)
        {
            super(renderer, entity, state, renderPartialTicks);
            this.setDensity(density);
        }

        public float getDensity()
        {
            return density;
        }

        public void setDensity(float density)
        {
            this.density = density;
        }
    }

    /**
     * Event that allows any feature to customize the rendering of fog.
     */

    public static class RenderFogEvent extends EventEntityViewRender
    {
        private final int fogMode;
        private final float farPlaneDistance;

        public RenderFogEvent(EntityRenderer renderer, Entity entity, IBlockState state, double renderPartialTicks, int fogMode, float farPlaneDistance)
        {
            super(renderer, entity, state, renderPartialTicks);
            this.fogMode = fogMode;
            this.farPlaneDistance = farPlaneDistance;
        }

        public int getFogMode()
        {
            return fogMode;
        }

        public float getFarPlaneDistance()
        {
            return farPlaneDistance;
        }
    }

    /**
     * Event that allows any feature to customize the color of fog the player sees.
     * NOTE: Any change made to one of the color variables will affect the result seen in-game.
     */
    public static class FogColors extends EventEntityViewRender
    {
        private float red;
        private float green;
        private float blue;

        public FogColors(EntityRenderer renderer, Entity entity, IBlockState state, double renderPartialTicks, float red, float green, float blue)
        {
            super(renderer, entity, state, renderPartialTicks);
            this.setRed(red);
            this.setGreen(green);
            this.setBlue(blue);
        }

        public float getRed() { return red; }
        public void setRed(float red) { this.red = red; }
        public float getGreen() { return green; }
        public void setGreen(float green) { this.green = green; }
        public float getBlue() { return blue; }
        public void setBlue(float blue) { this.blue = blue; }
    }
    
    /** 
     * Event that allows mods to alter the angles of the player's camera. Mainly useful for applying roll.
     */
    public static class CameraSetup extends EventEntityViewRender
    {
        private float yaw;
        private float pitch;
        private float roll;

        public CameraSetup(EntityRenderer renderer, Entity entity, IBlockState state, double renderPartialTicks, float yaw, float pitch, float roll)
        {
            super(renderer, entity, state, renderPartialTicks);
            this.setYaw(yaw);
            this.setPitch(pitch);
            this.setRoll(roll);
        }

        public float getYaw() { return yaw; }
        public void setYaw(float yaw) { this.yaw = yaw; }
        public float getPitch() { return pitch; }
        public void setPitch(float pitch) { this.pitch = pitch; }
        public float getRoll() { return roll; }
        public void setRoll(float roll) { this.roll = roll; }
    }
    
    /**
     * Event that allows mods to alter the raw FOV itself.
     * This directly affects to the FOV without being modified.
     * */
    public static class FOVModifier extends EventEntityViewRender
    {
        private float fov;
        
        public FOVModifier(EntityRenderer renderer, Entity entity, IBlockState state, double renderPartialTicks, float fov) {
            super(renderer, entity, state, renderPartialTicks);
            this.setFOV(fov);
        }

        public float getFOV() {
            return fov;
        }

        public void setFOV(float fov) {
            this.fov = fov;
        }
    }
}

