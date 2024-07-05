import { Materia } from "./materia.interface";

export interface IndirizzoScolastico {
    id?: number,
    nome: string,
    materie: Materia[]
}
