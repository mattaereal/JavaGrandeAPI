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


import moldyn.*;
import montecarlo.*;
import raytracer.*;

import jgfutil.*;

public class JGFAllSizeB{

  public static int nthreads;

  public static void main(String argv[]){
   
    int size = 1; 

  if(argv.length != 0 ) {
    nthreads = Integer.parseInt(argv[0]);
  } else {
    System.out.println("The no of threads has not been specified, defaulting to 1");
    System.out.println("  ");
    nthreads = 1;
  }

    JGFInstrumentor.printHeader(3,size,nthreads);

    System.out.println("Starting MolDyn.");
    JGFMolDynBench mdb = new JGFMolDynBench(nthreads);
    mdb.JGFrun(size);
    System.out.println("-------------------------------\n");

    System.out.println("Starting MonteCarlo.");
    JGFMonteCarloBench mcb = new JGFMonteCarloBench(nthreads);
    mcb.setMode("exec");
    mcb.JGFrun(size);
    System.out.println("-------------------------------\n");

    System.out.println("Starting RayTracer.");
    JGFRayTracerBench rtb = new JGFRayTracerBench(nthreads);
    rtb.JGFrun(size);

  }
}


