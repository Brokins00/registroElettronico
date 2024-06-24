import { AnnoScolastico } from "./anno-scolastico.interface";
import { IndirizzoScolastico } from "./indirizzo-scolastico.interface";
import { Studente } from "./studente.interface";

export interface Classe {
    id?: number,
    nome: string,
    indirizzo: IndirizzoScolastico,
    annoScolastico: AnnoScolastico,
    studenti: Studente[]
}
