import { AnnoScolastico } from "./anno-scolastico.interface";
import { Docente } from "./docente.interface";
import { Materia } from "./materia.interface";

export interface Valutazione {
    id?: number,
    voto: number,
    descrizione: string,
    tipologia: string,
    annoScolastico: AnnoScolastico,
    data: string,
    docente: Docente,
    materia: Materia
}
