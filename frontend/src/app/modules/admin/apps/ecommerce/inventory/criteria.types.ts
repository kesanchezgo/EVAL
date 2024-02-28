export interface Subcriterion {
    id: number;
    name: string;
    description: string;
    weight: number;
    range1: number;
    range2: number;
  }
  
  export interface Criterion {
    id: number;
    name: string;
    description: string;
    weight: number;
    subcriteria: Subcriterion[];
  }
  
  /* export interface CriteriaResponse {
    criteria: Criterion[];
  } */

  export type CriteriaResponse = Criterion[];