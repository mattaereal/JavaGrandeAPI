/**************************************************************************
*                                                                         *
*         Java Grande Forum Benchmark Suite - Thread Version 1.0          *
*                                                                         *
*                            produced by                                  *
*                                                                         *
*                  Java Grande Benchmarking Project                       *
*                                                                         *
*                                at                                       *
*                                                                         *
*                Edinburgh Parallel Computing Centre                      *
*                                                                         * 
*                email: epcc-javagrande@epcc.ed.ac.uk                     *
*                                                                         *
*                                                                         *
*      This version copyright (c) The University of Edinburgh, 2001.      *
*                         All rights reserved.                            *
*                                                                         *
**************************************************************************/


package raytracer; 

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import jgfutil.*;
import moldyn.JGFMolDynBench; 

public class JGFRayTracerBench extends RayTracer implements JGFSection3 {

  public static int nthreads;
  public static long checksum1=0;
  public static int staticnumobjects;
 
  public JGFRayTracerBench(int nthreads) {
        this.nthreads=nthreads;
  }


  public void JGFsetsize(int size){
    this.size = size;
  }

  public void JGFinitialise(){


    // set image size 
    width = height = datasizes[size]; 

  }

  public void JGFApp() {
	  
	  
  }

  @SuppressWarnings("unchecked")
public void JGFapplication(){ 


      CyclicBarrier cbr =  new CyclicBarrier(nthreads);
      ExecutorService executor = Executors.newFixedThreadPool(nthreads);
      List<Callable<Object>> callables = new ArrayList<Callable<Object>>();
      
      for(int i = 0; i < nthreads; i++) {
          callables.add(new RayTracerCaller(i, width, height, cbr));
      }
      
      try {
          executor.invokeAll(callables);
          executor.shutdown();
          System.out.println("Shutting down executor.");
          executor.awaitTermination(5, TimeUnit.SECONDS);
          System.out.println("Awaiting termination.");
      } catch (InterruptedException e) {
          System.out.println("Tasks interrupted.");
      } finally {
          if (!executor.isTerminated()) {
              System.err.println("Canceling non-finished tasks.");
          }
          executor.shutdownNow();
          System.out.println("Shutdown finished");
      }

  } 


  public void JGFvalidate(){
    long refval[] = {2676692,29827635};
    long dev = checksum1 - refval[size]; 
    if (dev != 0 ){
      System.out.println("Validation failed"); 
      System.out.println("Pixel checksum = " + checksum1);
      System.out.println("Reference value = " + refval[size]); 
    }
  }

  public void JGFtidyup(){    
    scene = null;  
    lights = null;  
    prim = null;  
    tRay = null;  
    inter = null;  

    System.gc(); 
  }


  public void JGFrun(int size){

    JGFInstrumentor.addTimer("Section3:RayTracer:Total", "Solutions",size);
    JGFInstrumentor.addTimer("Section3:RayTracer:Init", "Objects",size);
    JGFInstrumentor.addTimer("Section3:RayTracer:Run", "Pixels",size);

    JGFsetsize(size); 

    JGFInstrumentor.startTimer("Section3:RayTracer:Total");

    JGFinitialise(); 
    JGFapplication(); 
    JGFvalidate(); 
    JGFtidyup(); 

    JGFInstrumentor.stopTimer("Section3:RayTracer:Total");

    JGFInstrumentor.addOpsToTimer("Section3:RayTracer:Init", (double) staticnumobjects);
    JGFInstrumentor.addOpsToTimer("Section3:RayTracer:Run", (double) (width*height));
    JGFInstrumentor.addOpsToTimer("Section3:RayTracer:Total", 1);

    JGFInstrumentor.printTimer("Section3:RayTracer:Init");
    JGFInstrumentor.printTimer("Section3:RayTracer:Run"); 
    JGFInstrumentor.printTimer("Section3:RayTracer:Total"); 
  }


}
