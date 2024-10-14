import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, finalize } from 'rxjs/operators';
import { of } from 'rxjs';
import {Activite} from "../../models/activite.model";
import {ActiviteService} from "../../services/activite.service";


@Component({
  selector: 'app-activite-list',
  templateUrl: './activite-list.component.html',
  styleUrls: ['./activite-list.component.css']
})
export class ActiviteListComponent implements OnInit {
  activites: Activite[] = [];
  isLoading = true;
  error: string | null = null;
  utilisateurId: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private activiteService: ActiviteService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const userId = params.get('userId');
      if (userId) {
        this.utilisateurId = +userId;
        this.loadActivites(this.utilisateurId);
      } else {
        console.error('No user ID provided in route parameters');
        this.error = 'Utilisateur ID not provided';
        this.isLoading = false;
      }
    });
  }

  private loadActivites(userId: number): void {
    this.isLoading = true;
    this.error = null;

    this.activiteService.getActivitesByUtilisateurId(userId)
      .pipe(
        catchError(err => {
          console.error('Error fetching activities:', err);
          this.error = 'Failed to load activities. Please try again later.';
          return of([] as Activite[]);
        }),
        finalize(() => {
          this.isLoading = false;
        })
      )
      .subscribe(activites => {
        this.activites = activites;
      });
  }

  createActivite(): void {
    if (this.utilisateurId) {
      this.router.navigate(['/user/activite-create', this.utilisateurId]);
    }
  }

  updateActivite(id: number): void {
    this.router.navigate(['/user/activite-update', id]);
  }

  deleteActivite(id: number): void {
    if (confirm('Are you sure you want to delete this activity?')) {
      this.activiteService.deleteActivite(id).subscribe({
        next: () => {
          this.activites = this.activites.filter(activite => activite.id !== id);
        },
        error: (err) => {
          console.error('Error deleting activity:', err);
        }
      });
    }
  }
}
