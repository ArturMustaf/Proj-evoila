import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {NavService} from '../../../../../../core/services/nav/nav.service';
import {ClrDatagridPagination, ClrDatagridStateInterface} from '@clr/angular';
import {SearchAbleGrid} from '../models/SearchAbleGrid';
import {SearchGridloaderService} from '../../../../service/form-grid/search-gridloader.service';
import {RequestServiceService} from '../../../../service/shared/request-service.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-searchable-grid',
  templateUrl: './searchable-grid.component.html',
  styleUrls: ['./searchable-grid.component.scss']
})
/**
 * Component which renders the current selected grid. The grid is identified by the last path string. This path string then is looked up
 * in the searchgridloader => if a grid is found for the string then the searchgrid definition of the grid is rendered
 * the route reuse strategy is disabled to force
 */
export class SearchableGridComponent implements OnInit {


  gridName: string;
  searchGridDefinition: SearchAbleGrid;
  data: any[];
  loading = true;
  searchQuery = '';
  page: ClrDatagridPagination;
  @Input()
  onlySelect = false;

  noSearch = false;
  @Input()
  editMode = true;
  @Input()
  embeddedGrid = false;


  public selectedData: any = undefined;
  @Input() inputGridName: string;

  constructor(public searchGridLoader: SearchGridloaderService,
              private requestService: RequestServiceService,
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
    this.router.navigate(['admin', ...this.searchGridDefinition.redirectUrl]);
  }

  ngOnInit(): void {
    if (!this.inputGridName) {
      this.route.params.subscribe(params => {
        this.gridName = params.path;
        this.searchGridDefinition = this.searchGridLoader.loadSearchGrid(this.gridName);
      });
    } else if (this.embeddedGrid) {
      this.gridName = this.inputGridName;
      this.searchGridDefinition = this.searchGridLoader.loadSearchGrid(this.gridName);
      this.noSearch = true;
    } else {
      this.gridName = this.inputGridName;
      this.onlySelect = true;
      this.searchGridDefinition = this.searchGridLoader.loadSearchGrid(this.gridName);
    }
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
    if (!this.onlySelect) {
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
      const search = await this.requestService.loadSearchData(this.searchGridDefinition.searchUrl,
        query, +this.page.currentPage - 1 + '', this.page.pageSize + '');
      this.data = search.data;
      this.page.totalItems = search.resultSize;

      this.loading = false;
    }
  }

}

