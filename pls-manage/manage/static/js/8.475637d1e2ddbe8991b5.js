webpackJsonp([8],{198:function(e,t,a){a(424);var n=a(86)(a(307),a(445),null,null);e.exports=n.exports},208:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=a(50),s=a.n(n),l=a(49),r=a.n(l),i=a(51),o=a.n(i),c=a(87),u=a(88),d=a(89);t.default={data:function(){return{baseUrl:u.a}},created:function(){this.adminInfo.id||this.getAdminData()},computed:o()({},a.i(d.b)(["adminInfo"])),methods:o()({},a.i(d.c)(["getAdminData"]),{handleCommand:function(e){var t=this;return r()(s.a.mark(function n(){var l;return s.a.wrap(function(n){for(;;)switch(n.prev=n.next){case 0:if("home"!=e){n.next=4;break}t.$router.push("/manage"),n.next=9;break;case 4:if("singout"!=e){n.next=9;break}return n.next=7,a.i(c.b)();case 7:l=n.sent,1==l.status?(t.$message({type:"success",message:"退出成功"}),t.$router.push("/")):t.$message({type:"error",message:l.message});case 9:case"end":return n.stop()}},n,t)}))()}})}},209:function(e,t,a){t=e.exports=a(187)(!1),t.push([e.i,".allcover{position:absolute;top:0;right:0}.ctt{left:50%;transform:translate(-50%,-50%)}.ctt,.tb{position:absolute;top:50%}.tb{transform:translateY(-50%)}.lr{position:absolute;left:50%;transform:translateX(-50%)}.header_container{background-color:#eff2f7;height:60px;display:-ms-flexbox;display:flex;-ms-flex-pack:justify;justify-content:space-between;-ms-flex-align:center;align-items:center;padding-left:20px}.avator{width:36px;height:36px;border-radius:50%;margin-right:37px}.el-dropdown-menu__item{text-align:center}",""])},210:function(e,t,a){var n=a(209);"string"==typeof n&&(n=[[e.i,n,""]]),n.locals&&(e.exports=n.locals);a(188)("02d08ad4",n,!0)},211:function(e,t,a){a(210);var n=a(86)(a(208),a(212),null,null);e.exports=n.exports},212:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"header_container"},[a("el-breadcrumb",{attrs:{separator:"/"}},[a("el-breadcrumb-item",{attrs:{to:{path:"/manage"}}},[e._v("首页")]),e._v(" "),e._l(e.$route.meta.nabs,function(t,n){return a("el-breadcrumb-item",{key:n},[e._v(e._s(t))])})],2),e._v(" "),a("el-dropdown",{attrs:{"menu-align":"start"},on:{command:e.handleCommand}},[a("img",{staticClass:"avator",attrs:{src:e.baseUrl+e.adminInfo.avatar}}),e._v(" "),a("el-dropdown-menu",{attrs:{slot:"dropdown"},slot:"dropdown"},[a("el-dropdown-item",{attrs:{command:"home"}},[e._v("首页")]),e._v(" "),a("el-dropdown-item",{attrs:{command:"singout"}},[e._v("退出")])],1)],1)],1)},staticRenderFns:[]}},307:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=a(50),s=a.n(n),l=a(49),r=a.n(l),i=a(211),o=a.n(i),c=a(88),u=a(87);t.default={data:function(){return{baseUrl:c.a,limit:10,count:0,tableData:[],currentPage:1,selectTable:{},dialogFormVisible:!1,menuSelectOptions:[],menuOptions:[],selectMenu:{},selectUnitMenu:{},selectIndex:null,selectItems:[],name:null,speciesId:null,expendRow:[],foodIds:[],queryForm:{speciesId:"",name:""},unitList:[{value:1,label:"元/斤"},{value:2,label:"元/瓶"},{value:3,label:"元/袋"},{value:4,label:"元/只"},{value:5,label:"元/个"}],multipleSelection:[]}},created:function(){},activated:function(){this.initData(),this.getMenu()},computed:{},components:{headTop:o.a},methods:{initData:function(){var e=this;return r()(s.a.mark(function t(){return s.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:try{e.getFoods()}catch(e){console.log("获取数据失败",e)}case 1:case"end":return t.stop()}},t,e)}))()},getMenu:function(){var e=this;return r()(s.a.mark(function t(){var n;return s.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return e.menuOptions=[],t.prev=1,t.next=4,a.i(u.d)();case 4:n=t.sent,(n.code=200)?(e.menuSelectOptions.push({label:"请选择分类",value:""}),n.data.forEach(function(t,a){e.menuOptions.push({label:t.name,value:t.id,index:a}),e.menuSelectOptions.push({label:t.name,value:t.id,index:a})})):console.log(n.message),t.next=11;break;case 8:t.prev=8,t.t0=t.catch(1),console.log("获取食品分类失败",t.t0);case 11:case"end":return t.stop()}},t,e,[[1,8]])}))()},getFoods:function(){var e=this;return r()(s.a.mark(function t(){var n;return s.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return t.next=2,a.i(u.g)({pageNo:e.currentPage,pageSize:e.limit,speciesId:e.queryForm.speciesId,name:e.queryForm.name});case 2:if(n=t.sent,200!=n.code){t.next=9;break}e.count=n.data.total,e.tableData=[],n.data.list.forEach(function(t,a){var n={};n.id=t.id,n.name=t.name,n.speciesId=t.speciesId,n.speciesName=t.speciesName,n.details=t.details,n.imgLink=t.imgLink,n.price=t.price,n.unitId=t.unitId,n.unitName=e.unitList[t.unitId-1].label,n.index=a,e.tableData.push(n)}),t.next=10;break;case 9:throw new Error(n.message);case 10:case"end":return t.stop()}},t,e)}))()},handleQuery:function(){this.initData()},handleSelectionChange:function(e){this.multipleSelection=e},tableRowClassName:function(e,t){return 1===t?"info-row":3===t?"positive-row":""},handleSizeChange:function(e){console.log("每页 "+e+" 条")},handleCurrentChange:function(e){this.currentPage=e,this.getFoods()},expand:function(e,t){if(t)this.getSelectItemData(e);else{var a=this.expendRow.indexOf(e.index);this.expendRow.splice(a,1)}},handleBatchDel:function(){var e=this;return r()(s.a.mark(function t(){var n;return s.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:if(t.prev=0,!(e.multipleSelection.length<=0)){t.next=4;break}return e.$message({type:"warning",message:"请选择食品数据！"}),t.abrupt("return",!1);case 4:return e.multipleSelection.forEach(function(t,a){e.foodIds.push(t.id)}),t.next=7,a.i(u.u)({foodIds:e.foodIds});case 7:if(n=t.sent,200!=n.code){t.next=13;break}e.$message({type:"success",message:"删除食品成功"}),e.initData(),t.next=14;break;case 13:throw new Error(n.message);case 14:e.multipleSelection=[],e.foodIds=[],t.next=22;break;case 18:t.prev=18,t.t0=t.catch(0),e.$message({type:"error",message:t.t0.message}),console.log("删除食品失败");case 22:case"end":return t.stop()}},t,e,[[0,18]])}))()},handleAdd:function(){this.$router.push("/addGoods")},handleEdit:function(e){console.log("获取菜单数据"),this.getSelectItemData(e),this.dialogFormVisible=!0},getSelectItemData:function(e){var t=this;return r()(s.a.mark(function a(){return s.a.wrap(function(a){for(;;)switch(a.prev=a.next){case 0:t.selectTable={id:e.id,name:e.name,speciesId:e.speciesId,speciesName:e.speciesName,details:e.details,imgLink:e.imgLink,price:e.price,unitId:e.unitId},t.selectTable.imgLink&&null!=t.selectTable.imgLink&&(t.selectTable.imgLink=t.selectTable.imgLink.replace(t.baseUrl,"")),t.selectMenu={label:e.speciesName,value:e.speciesId},t.selectUnitMenu=t.unitList[e.unitId-1];case 4:case"end":return a.stop()}},a,t)}))()},handleDelete:function(e,t){var n=this;return r()(s.a.mark(function l(){var r;return s.a.wrap(function(s){for(;;)switch(s.prev=s.next){case 0:return s.prev=0,n.foodIds.push(t.id),console.log(n.foodIds),s.next=5,a.i(u.u)({foodIds:n.foodIds});case 5:if(r=s.sent,200!=r.code){s.next=11;break}n.$message({type:"success",message:"删除食品成功"}),n.tableData.splice(e,1),s.next=12;break;case 11:throw new Error(r.message);case 12:n.foodIds=[],s.next=19;break;case 15:s.prev=15,s.t0=s.catch(0),n.$message({type:"error",message:s.t0.message}),console.log("删除食品失败");case 19:case"end":return s.stop()}},l,n,[[0,15]])}))()},handleServiceAvatarScucess:function(e,t){200==e.code?this.selectTable.imgLink=e.data:this.$message.error("上传图片失败！")},beforeAvatarUpload:function(e){var t="image/jpeg"===e.type||"image/png"===e.type,a=e.size/1024/1024<2;return t||this.$message.error("上传头像图片只能是 JPG 格式!"),a||this.$message.error("上传头像图片大小不能超过 2MB!"),t&&a},updateFood:function(){var e=this;return r()(s.a.mark(function t(){var n,l;return s.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return t.prev=0,n=e.selectTable,t.next=4,a.i(u.v)(n);case 4:l=t.sent,200==l.code?(e.dialogFormVisible=!1,e.$message({type:"success",message:"更新食品信息成功"}),e.getFoods()):e.$message({type:"error",message:l.message}),t.next=11;break;case 8:t.prev=8,t.t0=t.catch(0),console.log("更新食品信息失败",t.t0);case 11:case"end":return t.stop()}},t,e,[[0,8]])}))()}}}},320:function(e,t,a){t=e.exports=a(187)(!1),t.push([e.i,".allcover{position:absolute;top:0;right:0}.ctt{left:50%;transform:translate(-50%,-50%)}.ctt,.tb{position:absolute;top:50%}.tb{transform:translateY(-50%)}.lr{position:absolute;left:50%;transform:translateX(-50%)}.demo-table-expand{font-size:0}.demo-table-expand label{width:90px;color:#99a9bf}.demo-table-expand .el-form-item{margin-right:0;margin-bottom:0;width:50%}.table_container{padding:20px}.Pagination{display:-ms-flexbox;display:flex;-ms-flex-pack:start;justify-content:flex-start;margin-top:8px}.avatar-uploader .el-upload{border:1px dashed #d9d9d9;border-radius:6px;cursor:pointer;position:relative;overflow:hidden}.avatar-uploader .el-upload:hover{border-color:#20a0ff}.category_select2{width:600px;border:1px solid #eaeefb;padding:10px 30px 10px 10px;border-top-right-radius:6px;border-top-left-radius:6px}.avatar-uploader-icon{font-size:28px;color:#8c939d;width:120px;height:120px;line-height:120px;text-align:center}.avatar{width:120px;height:120px;display:block}",""])},424:function(e,t,a){var n=a(320);"string"==typeof n&&(n=[[e.i,n,""]]),n.locals&&(e.exports=n.locals);a(188)("58e55dee",n,!0)},445:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"fillcontain"},[a("head-top"),e._v(" "),a("div",{staticClass:"table_container"},[a("div",{staticClass:"demo-input-size"},[a("el-row",{staticClass:"category_select2"},[a("el-input",{staticStyle:{width:"200px"},attrs:{placeholder:"食品名称"},model:{value:e.queryForm.name,callback:function(t){e.$set(e.queryForm,"name",t)},expression:"queryForm.name"}}),e._v(" "),a("el-select",{staticStyle:{width:"200px"},attrs:{placeholder:"请选择食品分类"},model:{value:e.queryForm.speciesId,callback:function(t){e.$set(e.queryForm,"speciesId",t)},expression:"queryForm.speciesId"}},e._l(e.menuSelectOptions,function(e){return a("el-option",{key:e.value+"B",attrs:{label:e.label,value:e.value}})})),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:e.handleQuery}},[e._v("查询")])],1)],1),e._v(" "),a("div",{staticStyle:{"margin-bottom":"10px"}},[a("el-button",{attrs:{type:"primary"},on:{click:e.handleAdd}},[e._v("新增")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:e.handleBatchDel}},[e._v("批量删除")])],1),e._v(" "),a("el-table",{staticStyle:{width:"100%"},attrs:{data:e.tableData,"expand-row-keys":e.expendRow,"row-key":function(e){return e.index}},on:{expand:e.expand,"selection-change":e.handleSelectionChange}},[a("el-table-column",{attrs:{type:"selection",width:"55"}}),e._v(" "),a("el-table-column",{attrs:{type:"index",label:"序号",width:"100"}}),e._v(" "),a("el-table-column",{attrs:{label:"图片",prop:"imgLink"},scopedSlots:e._u([{key:"default",fn:function(e){return[a("img",{staticStyle:{width:"50px",height:"50px"},attrs:{src:e.row.imgLink,alt:""}})]}}])}),e._v(" "),a("el-table-column",{attrs:{label:"食品名称",prop:"name"}}),e._v(" "),a("el-table-column",{attrs:{label:"食品价格",prop:"price"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("label",[e._v(e._s(t.row.price+" "+t.row.unitName))])]}}])}),e._v(" "),a("el-table-column",{attrs:{label:"食品种类",prop:"speciesName"}}),e._v(" "),a("el-table-column",{attrs:{label:"食品详情",prop:"details"}}),e._v(" "),a("el-table-column",{attrs:{label:"操作",width:"160"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{size:"small"},on:{click:function(a){e.handleEdit(t.row)}}},[e._v("编辑\n                    ")]),e._v(" "),a("el-button",{attrs:{size:"small",type:"danger"},on:{click:function(a){e.handleDelete(t.$index,t.row)}}},[e._v("删除\n                    ")])]}}])})],1),e._v(" "),a("div",{staticClass:"Pagination"},[a("el-pagination",{attrs:{"current-page":e.currentPage,"page-size":10,layout:"total, prev, pager, next",total:e.count},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}})],1),e._v(" "),a("el-dialog",{attrs:{title:"修改食品信息"},model:{value:e.dialogFormVisible,callback:function(t){e.dialogFormVisible=t},expression:"dialogFormVisible"}},[a("el-form",{attrs:{model:e.selectTable}},[a("el-form-item",{attrs:{label:"食品名称","label-width":"100px"}},[a("el-input",{attrs:{"auto-complete":"off"},model:{value:e.selectTable.name,callback:function(t){e.$set(e.selectTable,"name",t)},expression:"selectTable.name"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"食品详情","label-width":"100px"}},[a("el-input",{model:{value:e.selectTable.details,callback:function(t){e.$set(e.selectTable,"details",t)},expression:"selectTable.details"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"食品价格","label-width":"100px"}},[a("el-input",{attrs:{placeholder:"食品价格参考：5.00"},model:{value:e.selectTable.price,callback:function(t){e.$set(e.selectTable,"price",t)},expression:"selectTable.price"}},[a("el-select",{staticStyle:{width:"120px"},attrs:{slot:"append",placeholder:e.selectUnitMenu.label,placeholder:"请选择单位"},slot:"append",model:{value:e.selectTable.unitId,callback:function(t){e.$set(e.selectTable,"unitId",t)},expression:"selectTable.unitId"}},e._l(e.unitList,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1)],1),e._v(" "),a("el-form-item",{attrs:{label:"食品分类","label-width":"100px"}},[a("el-select",{staticStyle:{width:"100%"},attrs:{placeholder:e.selectMenu.label,placeholder:"请选择食品分类"},model:{value:e.selectTable.speciesId,callback:function(t){e.$set(e.selectTable,"speciesId",t)},expression:"selectTable.speciesId"}},e._l(e.menuOptions,function(e){return a("el-option",{key:e.value+"A",attrs:{label:e.label,value:e.value}})}))],1),e._v(" "),a("el-form-item",{attrs:{label:"食品图片","label-width":"100px"}},[a("el-upload",{staticClass:"avatar-uploader",attrs:{action:e.baseUrl+"/api/upload/img","show-file-list":!1,"on-success":e.handleServiceAvatarScucess,"before-upload":e.beforeAvatarUpload,name:"imgFile"}},[e.selectTable.imgLink?a("img",{staticClass:"avatar",attrs:{src:e.selectTable.imgLink}}):a("i",{staticClass:"el-icon-plus avatar-uploader-icon"})])],1)],1),e._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.dialogFormVisible=!1}}},[e._v("取 消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:e.updateFood}},[e._v("确 定")])],1)],1)],1)],1)},staticRenderFns:[]}}});