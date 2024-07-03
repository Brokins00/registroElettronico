import { Indirizzo } from "./indirizzo.interface";
import { User } from "./user.interface";
import { Valutazione } from "./valutazione.interface";

export interface Studente extends User {
    nome: string;
    cognome: string;
    dataDiNascita: Date,
    codiceFiscale: string,
    indirizzo: Indirizzo,
    valutazioni: Valutazione[]
}
