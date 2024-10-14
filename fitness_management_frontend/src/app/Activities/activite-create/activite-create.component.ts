import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {ActiviteService} from "../../services/activite.service";
import {Activite} from "../../models/activite.model";


@Component({
  selector: 'app-activite-create',
  templateUrl: './activite-create.component.html',
  styleUrls: ['./activite-create.component.css']
})
export class ActiviteCreateComponent implements OnInit {
  activiteForm: FormGroup;
  imageFile: File | null = null;
  videoFile: File | null = null;
  isLoading = false;
  utilisateurId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private activiteService: ActiviteService,
    protected router: Router,
    private route: ActivatedRoute
  ) {
    this.activiteForm = this.fb.group({
      date: ['', Validators.required],
      pas: [0, [Validators.required, Validators.min(0)]],
      distance: [0, [Validators.required, Validators.min(0)]],
      caloriesBrulees: [0, Validators.required],
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const userId = params.get('userId');
      if (userId) {
        this.utilisateurId = +userId;
      } else {
        console.error('No user ID found');
      }
    });
  }

  onImageSelected(event: any): void {
    this.imageFile = event.target.files[0];
  }

  onVideoSelected(event: any): void {
    this.videoFile = event.target.files[0];
  }

  onSubmit(): void {
    if (this.activiteForm.invalid || !this.utilisateurId) return;

    this.isLoading = true;
    const activite: Activite = { ...this.activiteForm.value, utilisateurId: this.utilisateurId };

    this.activiteService.createActivite(activite, this.imageFile ?? undefined, this.videoFile ?? undefined).subscribe({
      next: () => {
        this.router.navigate(['/user/activite-list', this.utilisateurId]);
      },
      error: (err) => {
        console.error('Failed to create activity', err);
      },
      complete: () => {
        this.isLoading = false;
      }
    });
  }
}
