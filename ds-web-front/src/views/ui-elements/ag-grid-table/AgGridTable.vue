<!-- =========================================================================================
    File Name: AgGridTable.vue
    Description: Ag Grid table
    ----------------------------------------------------------------------------------------
    Item Name: Vuexy - Vuejs, HTML & Laravel Admin Dashboard Template
    Author: Pixinvent
    Author URL: http://www.themeforest.net/user/pixinvent
========================================================================================== -->


<template>
  <div id="ag-grid-demo">
    <vx-card>

      <!-- TABLE ACTION ROW -->
      <div class="flex flex-wrap justify-between items-center">

        <!-- ITEMS PER PAGE -->
        <div class="mb-4 md:mb-0 mr-4 ag-grid-table-actions-left">
          <vs-dropdown vs-trigger-click class="cursor-pointer">
            <div
              class="p-4 border border-solid d-theme-border-grey-light rounded-full d-theme-dark-bg cursor-pointer flex items-center justify-between font-medium">
              <!--              <span class="mr-2">{{ currentPage * paginationPageSize - (paginationPageSize - 1) }} - {{ contacts.length - currentPage * paginationPageSize > 0 ? currentPage * paginationPageSize : contacts.length }} of {{ contacts.length }}</span>-->
              <span class="mr-2">{{ currentPage * paginationPageSize - (paginationPageSize - 1) }} - {{ total - currentPage * paginationPageSize > 0 ? currentPage * paginationPageSize : total.length }} of {{ total }}</span>
              <feather-icon icon="ChevronDownIcon" svgClasses="h-4 w-4"/>
            </div>
            <!-- <vs-button class="btn-drop" type="line" color="primary" icon-pack="feather" icon="icon-chevron-down"></vs-button> -->
            <vs-dropdown-menu>

              <vs-dropdown-item @click="gridApi.paginationSetPageSize(20)">
                <span>20</span>
              </vs-dropdown-item>
              <vs-dropdown-item @click="gridApi.paginationSetPageSize(50)">
                <span>50</span>
              </vs-dropdown-item>
              <vs-dropdown-item @click="gridApi.paginationSetPageSize(100)">
                <span>100</span>
              </vs-dropdown-item>
              <vs-dropdown-item @click="gridApi.paginationSetPageSize(150)">
                <span>150</span>
              </vs-dropdown-item>
            </vs-dropdown-menu>
          </vs-dropdown>
        </div>

        <!-- TABLE ACTION COL-2: SEARCH & EXPORT AS CSV -->
        <div class="flex flex-wrap items-center justify-between ag-grid-table-actions-right">
          <vs-input class="mb-4 md:mb-0 mr-4" v-model="searchQuery" @blur="updateSearchQuery" placeholder="Search..."/>
          <vs-button class="mb-4 md:mb-0" @click="gridApi.exportDataAsCsv()">Export as CSV</vs-button>
        </div>
      </div>
      <ag-grid-vue
        ref="agGridTable"
        :gridOptions="gridOptions"
        class="ag-theme-material w-100 my-4 ag-grid-table"
        :columnDefs="columnDefs"
        :defaultColDef="defaultColDef"
        :rowData="contacts"
        rowSelection="multiple"
        colResizeDefault="shift"
        :animateRows="true"
        :floatingFilter="true"
        :pagination="true"
        :paginationPageSize="paginationPageSize"
        :suppressPaginationPanel="true"
        :enableRtl="$vs.rtl">
      </ag-grid-vue>
      <vs-pagination
        :total="totalPages"
        :max="maxPageNumbers"
        v-model="currentPage"/>

    </vx-card>
  </div>
</template>

<script>
import {AgGridVue} from "ag-grid-vue"
import contacts from './data.json'
import _ from "lodash";

import '@/assets/scss/vuexy/extraComponents/agGridStyleOverride.scss'

export default {
  components: {
    AgGridVue
  },
  data() {
    return {
      searchQuery: '',
      gridOptions: {},
      maxPageNumbers: 7,
      gridApi: null,
      total: 0,
      defaultColDef: {
        sortable: true,
        editable: true,
        resizable: true,
        suppressMenu: true
      },
      columnDefs: [
        {
          headerName: '设备号',
          field: 'deviceCode',
          width: 275,
          filter: true,
          checkboxSelection: true,
          headerCheckboxSelectionFilteredOnly: true,
          headerCheckboxSelection: true,
        },
        {
          headerName: '里程',
          field: 'mileage',
          filter: true,
          width: 175,
        }
        // {
        //   headerName: 'Email',
        //   field: 'email',
        //   filter: true,
        //   width: 250,
        //   pinned: 'left'
        // },
        // {
        //   headerName: 'Company',
        //   field: 'company',
        //   filter: true,
        //   width: 250,
        // },
        // {
        //   headerName: 'City',
        //   field: 'city',
        //   filter: true,
        //   width: 150,
        // },
        // {
        //   headerName: 'Country',
        //   field: 'country',
        //   filter: true,
        //   width: 150,
        // },
        // {
        //   headerName: 'State',
        //   field: 'state',
        //   filter: true,
        //   width: 125,
        // },
        // {
        //   headerName: 'Zip',
        //   field: 'zip',
        //   filter: true,
        //   width: 125,
        // },
        // {
        //   headerName: 'Followers',
        //   field: 'followers',
        //   filter: "agNumberColumnFilter",
        //   width: 125,
        // },
      ],
      contacts: [],
    }
  },
  watch: {
    '$store.state.windowWidth'(val) {
      if (val <= 576) {
        this.maxPageNumbers = 4
        this.gridOptions.columnApi.setColumnPinned('deviceCode', null)
      } else this.gridOptions.columnApi.setColumnPinned('deviceCode', 'left')
    },
    currentPage: function (newPage, oldPage) {
      console.log("newPage:%d,oldPage:%d", newPage, oldPage);
      this.loadData();
    }
  },
  computed: {
    paginationPageSize() {
      if (this.gridApi) {
        return this.gridApi.paginationGetPageSize()
      } else return 50
    },
    totalPages() {
      if (this.total > 0) return Math.ceil(this.total / this.gridApi.paginationGetPageSize());
      if (this.gridApi) return this.gridApi.paginationGetTotalPages()
      else return 0
    },
    currentPage: {
      get() {
        if (this.gridApi) return this.gridApi.paginationGetCurrentPage() + 1
        else return 1
      },
      set(val) {
        this.gridApi.paginationGoToPage(val - 1)
      }
    }
  },
  methods: {
    updateSearchQuery(val) {
      const me = this;
      console.log("updateSearchQuery:%s", me.searchQuery);
      me.loadData();
      // this.gridApi.setQuickFilter(val)
    },
    loadData() {
      const me = this;
      let dataUrl = "/api/contest/sample/findPageList/" + me.currentPage + "/" + me.paginationPageSize;
      if (!_.isEmpty(me.searchQuery)) {
        dataUrl = dataUrl.concat("?deviceCode=").concat(me.searchQuery);
      }

      // get all vehicle data
      me.$http.get(dataUrl)
        .then((response) => {
          console.log(response);
          me.contacts = response.data.list;
          me.total = response.data.totalData;
        })
        .catch((error) => {
          console.log(error);
          me.$vs.notify({
            title: "",
            text: error,
            color: 'danger',
            position: 'top-right'
          });
        })
    }
  },
  mounted() {
    this.gridApi = this.gridOptions.api
    this.contacts = contacts;
    this.loadData();

    /* =================================================================
      NOTE:
      Header is not aligned properly in RTL version of agGrid table.
      However, we given fix to this issue. If you want more robust solution please contact them at gitHub
    ================================================================= */
    if (this.$vs.rtl) {
      const header = this.$refs.agGridTable.$el.querySelector(".ag-header-container")
      header.style.left = "-" + String(Number(header.style.transform.slice(11, -3)) + 9) + "px"
    }
  }
}

</script>
