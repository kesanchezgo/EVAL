export interface ProjectEvaluationResponse {
    content: ProjectEvaluationContentItem[];
    pageable: ProjectEvaluationPageable;
   /*  paginationInfo: PaginationInfo; */
    last: boolean;
    totalPages: number;
    totalElements: number;
    first: boolean;
    size: number;
    number: number;
    sort: {
      empty: boolean;
      sorted: boolean;
      unsorted: boolean;
    };
    numberOfElements: number;
    empty: boolean;
  }
  
  export interface ProjectEvaluationContentItem {
    id: number;
    project: ProjectEvaluation;
    score: number;
  }
  
  export interface ProjectEvaluation {
    id: number;
    externalId: string;
    name: string;
    area1: string | null;
    area2: string | null;
    status: string;
    condition: string;
    author: string;
  }
  
  export interface ProjectEvaluationPageable {
    sort: {
      empty: boolean;
      sorted: boolean;
      unsorted: boolean;
    };
    offset: number;
    pageNumber: number;
    pageSize: number;
    paged: boolean;
    unpaged: boolean;
  }

  export interface PaginationInfo {
    last: boolean;
    totalPages: number;
    totalElements: number;
    first: boolean;
    size: number;
    number: number;
    sort: {
      empty: boolean;
      sorted: boolean;
      unsorted: boolean;
    };
    numberOfElements: number;
    empty: boolean;
  }
  
  