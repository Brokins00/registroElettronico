import { Istituto } from "./istituto.interface";

export interface User {
    id?: number,
    istituto: Istituto,
    email: string,
    password: string,
    ruoloUtente: string,
}
