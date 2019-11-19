export class RetornoMessage {
    resultado: Resultado;
    erros: Erro[];

    constructor() { }
}

export class Resultado {
    id: number;
    tipoRegistro: number;
}

export class Erro {
    codErro: number;
    msgErro: string;
}