export interface Personne {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  genre: string;
  role: string;
  motDePasse :string;
  profileImage?: {
    id: number;
    imageUrl: string;
    cloudinaryImageId: string;
  };
}
