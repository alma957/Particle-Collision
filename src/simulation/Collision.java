package simulation;

public abstract class Collision extends AbstractEvent{
    private final Particle[]  particles; // Particles involved in the collision (can contain null if a wall is involved)
    private final int[] initial_hits; // Number of hits of the particles when the collision is predicted

    /**
     * Constructor for Collision
     */
    public Collision(final double t, final Particle[] ps) {
        super(t);
        this.particles = ps;

        // Initialise the number of hits of the particles:
        initial_hits = new int[particles.length];
        for (int i = 0; i < particles.length; i++){
            if (particles[i]!= null) this.initial_hits[i] = particles[i].collisions();
            else this.initial_hits[i] = -1; // For null particles (walls)
        }
    }

    /**
     * Returns true if this Collision is (still) valid.
     */
    @Override
    public boolean isValid() {
        // It is not valid if any of the particles has hit other particle/wall since the prediction was made:
        for (int i = 0; i < particles.length; i++){
            if (particles[i]!= null){
                if (initial_hits[i] != particles[i].collisions()) return false;
            }
        }
        return true;
    }

    /**
     * Returns an array containing the Particles involved in this Collision.
     */
    public Particle[] getParticles() {
        return particles;
    }
}
