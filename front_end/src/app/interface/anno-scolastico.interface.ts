import { Classe } from "./classe.interface";
import { Docente } from "./docente.interface";
import { Festivita } from "./festivita.interface";
import { Studente } from "./studente.interface";

export interface AnnoScolastico {
    id?: number,
    inizio: string,
    fine: string,
    festivita: Festivita[],
    classi: Classe[],
    studenti: Studente[],
    docenti: Docente[]
}
