package simulation;

import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import utils.MinPriorityQueue;

public class ParticleSimulation implements Runnable, ParticleEventHandler{

    private static final long FRAME_INTERVAL_MILLIS = 40;
    
    private final ParticlesModel          model;
    private final ParticlesView           screen;
    private MinPriorityQueue<Event>       queue;
    private double                        now;

    /**
     * Constructor for ParticleSimulation.
     */
    public ParticleSimulation(String name, ParticlesModel m) {
        this.model = m;
        this.screen = new ParticlesView(name, m); // To display the given model and name

        // Mantain the clock:
        this.now = 0;

        // Initialise the queue of events:
        this.queue = new MinPriorityQueue<Event>();
        this.queue.add(new Tick(1));

        //Add the predicted collisions from the initial state:
        Iterable<Collision> predicted_collisions= this.model.predictAllCollisions(now);
        for (Collision collision : predicted_collisions){
            this.queue.add(collision);
        }
    }

    /**
     * Runs the simulation.
     */
    @Override
    public void run() {
        try {
            SwingUtilities.invokeAndWait(screen);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        while (! this.queue.isEmpty()){
            Event next_event = this.queue.remove();
            if(next_event.isValid()){
                // Move the particles for the elapsed time:
                model.moveParticles(next_event.time() - this.now);

                // Update time (only progresses when events happen): 
                this.now = next_event.time();

                // Use ParticleSimulation as a event handler to tell the event to happen:
                next_event.happen(this); 
            }
        }
    }
    
    /**
     * Makes a tick happen (pauses the simulation, updates the display and adds a new tick to the queue)
     */
    @Override
    public void reactTo(Tick tick) {
        try {
            Thread.sleep(FRAME_INTERVAL_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.screen.update();
        this.queue.add(new Tick(now + 1));
    }

    /**
     *  Makes a collision happen (adds the predicted collisions of the involved particle(s) to the queue)
     */
    @Override
    public void reactTo(Collision collision) {
        Particle[] particles = collision.getParticles();
        for (Particle particle : particles){
            // Predict the collisions of each particle: 
            Iterable<Collision> new_collisions = model.predictCollisions(particle, this.now);

            // Add them to the queue:
            for (Collision new_collision : new_collisions){
                this.queue.add(new_collision);
            }
        }
    }
}
