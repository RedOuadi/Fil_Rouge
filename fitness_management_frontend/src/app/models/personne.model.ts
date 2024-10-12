import {Image} from "./image.model";
export interface Personne {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  genre: string;
  role: string;
  motDePasse :string;
  profileImage?: Image;

}

