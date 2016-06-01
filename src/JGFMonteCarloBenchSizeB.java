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


import montecarlo.*;

import jgfutil.*;

public class JGFMonteCarloBenchSizeB{ 

  public static int nthreads;
  public static String mode;

  public static void main(String argv[]){

  
  if(argv.length == 2 ) {
    nthreads = Integer.parseInt(argv[0]);
    mode = argv[1];
  } else {
    System.out.println("The no of threads has not been specified, defaulting to 1");
    System.out.println("  ");
    nthreads = 1;
    mode = "simple";
  }
      
    JGFMonteCarloBench mc = new JGFMonteCarloBench(nthreads); 
    mc.setMode(mode);
    JGFInstrumentor.printHeader(3,1,nthreads);   
    mc.JGFrun(1);
 
  }
}


