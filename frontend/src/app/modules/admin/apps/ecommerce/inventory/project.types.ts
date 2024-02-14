export interface ProjectAuthor {
    id: number;
    name: string;
    dni: string;
    email: string | null;
    cui: string | null;
    scopusId: string | null;
    orcid: string | null;
    renacyt: string | null;
    description: string | null;
    community: string | null;
}

export interface ProjectLabel {
    id: number;
    name: string;
    value: string;
}

export interface Project {
    id: string;
    name: string;
    doi: string | null;
    isbn: string | null;
    handle: string;
    issn: string | null;
    dateCreated: string;
    dateUpdated: string | null;
    description: string | null;
    originUrl: string;
    fileUrl: string;
    faculty: string;
    authors: ProjectAuthor[];
    labels: ProjectLabel[];
}

export interface PaginationSort {
    empty: boolean;
    sorted: boolean;
    unsorted: boolean;
}

export interface Pageable {
    sort: PaginationSort;
    offset: number;
    pageNumber: number;
    pageSize: number;
    paged: boolean;
    unpaged: boolean;
}

export interface ProjectResponse {
    content: Project[];
    pageable: Pageable;
    last: boolean;
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
    sort: PaginationSort;
    first: boolean;
    numberOfElements: number;
    empty: boolean;
}

export interface Proyecto {
    id_proyecto: string;
    codigo_conv: string;
    id_convocatoria: string;
    nomb_conv: string;
    id_esquema_finan: string;
    nomb_esq_finan: string;
    numero_cont: string | null;
    codigo_proy: string | null;
    nomb_proy: string;
    busqueda_nomb_proy: string;
    principal: string;
    busqueda_principal: string;
    estado_proyecto: string;
    estado_contrato: string;
    monto_ppto_item: number | null;
    monto_cont: string;
    claves_proy: string | null;
    busqueda_claves_proy: string | null;
    resumen_ejecutivo: string | null;
    obj_general_proy: string | null;
    est_proy: string;
    obs_proy: string | null;
    impacto_proy: string | null;
    resul_espe_proy: string | null;
    hipo_proy: string | null;
    just_proy: string | null;
    fech_ini_proy: string;
    durac_proy: string | null;
    id_persona: string;
    integrantes: string;
    id_persona_integrante: string;
    facultades: string;
    departamentos: string;
    busqueda_integrantes: string;
    monitor: string;
    id_area_prioritaria: string | null;
    id_disciplinaocde: string | null;
    id_area_investigacion: string | null;
    id_linea_investigacion: string | null;
    nomb_areapri: string | null;
    est_cont: string;
    area_investigacion: string | null;
    linea_investigacion: string | null;
    id_anio: string;
    id_investigador: string | null;
    contrato_id_persona: string;
    id_tiposubvencionado: string | null;
    tiposubven: string | null;
    id_coordinador: string;
    coordinador_nombres: string;
    dni_principal: string;
    sys_fech_registro: string;
    area_ocde: string | null;
    sub_area_ocde: string | null;
    disciplina_ocde: string | null;
    fase_estado_proyecto: string;
    id_anio_conv: string;
    id_proyect: string | null;
    total_hitos: number | null;
    informes_pendientes: number | null;
    informes_registrados: number | null;
    informes_enviados_revision: number | null;
    informes_observados: number | null;
    informes_aprobados: number | null;
    informes_desaprobados: number | null;
    informes_aprobados_reservas: number | null;
    reportes_registrados: number | null;
    reportes_enviados: number | null;
    reportes_observados: number | null;
    reportes_aprobados: number | null;
    reportes_pendientes: number | null;
    reportes_estados: string | null;
    informes_estados: string | null;
    fech_ini_cont: string;
    fech_fin_cont: string;
}

