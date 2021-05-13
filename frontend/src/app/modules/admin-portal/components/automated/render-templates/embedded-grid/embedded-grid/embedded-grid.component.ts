import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {SearchAbleGrid} from '../../../linking-table/models/SearchAbleGrid';
import {ClrDatagridPagination, ClrDatagridStateInterface} from '@clr/angular';
import {SearchGridloaderService} from '../../../../../service/form-grid/search-gridloader.service';
import {NavService} from '../../../../../../../core/services/nav/nav.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ApiserviceService} from '../../../../../service/apiservice.service';

@Component({
  selector: 'app-embedded-grid',
  templateUrl: './embedded-grid.component.html',
  styleUrls: ['./embedded-grid.component.scss']
})
/**
 * This was the idea for embedding a gridlayout in the forms dynamically to allow pagination over many objects
 * This conflicted with the merge policy of the save button resulting in possible losses and would need a function in the backend
 * to only load unconflicted datasets
 */
export class EmbeddedGridComponent implements OnInit {


  gridName: string;
  @Input()
  searchGridDefinition: SearchAbleGrid;
  data: any[];
  loading = true;
  searchQuery = '';
  page: ClrDatagridPagination;
  @Input()
  editMode = true;


  public selectedData: any = undefined;
  @Input() inputGridName: string;

  constructor(public searchGridLoader: SearchGridloaderService,
              private requestService: ApiserviceService,
              private nav: NavService,
              private route: ActivatedRoute,
              private router: Router) {
    nav.isAdmin = true;
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }

  @ViewChild('pagination')
  set pagination(pagination: any) {
    this.page = pagination;
    if (this.loading) {
      this.loadData();
    }
  }

  /**
   * This is called to create a new element of the element shown in the grid
   */
  toForms(): void {
    if (this.editMode) {
      this.router.navigate(['admin', ...this.searchGridDefinition.redirectUrl]);
    }
  }

  ngOnInit(): void {
    this.gridName = this.inputGridName;
    this.searchGridDefinition = this.searchGridLoader.loadSearchGrid(this.gridName);
  }

  refresh(state: ClrDatagridStateInterface): void {
    this.loadData();
  }

  search(): void {
    this.loadData();
  }

  /**
   * redirects to the right sub url to render the detail view of the clicked item
   * @param id of the element clicked
   */
  select(id: string): void {
    if (this.editMode) {
      this.router.navigate(['/admin', ...this.searchGridDefinition.redirectUrl, id]);
    }
  }

  /**
   * requests the search data to load out of the backend
   */
  async loadData(): Promise<void> {
    if (this.page) {
      const query = this.searchQuery === '' ? ' ' : this.searchQuery;
      this.loading = true;

      // this.data = await this.requestService.sendRequest(this.searchGridDefinition.apiUrl, this.page.currentPage - 1,this.page.pageSize,);
      // this.page.totalItems = search.resultSize;

      this.loading = false;
    }
  }

}


