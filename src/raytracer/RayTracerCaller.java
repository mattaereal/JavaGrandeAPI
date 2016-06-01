package raytracer;

import java.util.concurrent.Callable;

import jgfutil.JGFInstrumentor;

public class RayTracerCaller extends RayTracer implements Callable  {

    int id,height,width;
    Barrier br;

    public RayTracerCaller(int id,int width,int height,Barrier br) {
        this.id = id;
        this.width=width;
        this.height=height;
        this.br=br;

        JGFInstrumentor.startTimer("Section3:RayTracer:Init");

// create the objects to be rendered
        scene = createScene();

// get lights, objects etc. from scene.
        setScene(scene);

        numobjects = scene.getObjects();
        JGFRayTracerBench.staticnumobjects = numobjects;

        JGFInstrumentor.stopTimer("Section3:RayTracer:Init");

    }

    @Override
    public Object call() throws Exception {
        System.out.println("Starting: " + Thread.currentThread().getName());
        // Set interval to be rendered to the whole picture
        // (overkill, but will be useful to retain this for parallel versions)

        Interval interval = new Interval(0,width,height,0,height,1,id);

// synchronise threads and start timer

        br.DoBarrier(id);
        if(id == 0) JGFInstrumentor.startTimer("Section3:RayTracer:Run");

        render(interval);

        // Signal this thread has done iteration


        synchronized(scene){
        for(int i=0;i<JGFRayTracerBench.nthreads;i++)
            if (id==i) JGFRayTracerBench.checksum1=JGFRayTracerBench.checksum1+checksum;
        }

// synchronise threads and stop timer

        br.DoBarrier(id);
        if(id == 0) JGFInstrumentor.stopTimer("Section3:RayTracer:Run");  

        System.out.println("Finishing: " + Thread.currentThread().getName());
        return null;
    }
}
 