webpackJsonp([11],{195:function(e,t,o){o(432);var a=o(86)(o(304),o(453),null,null);e.exports=a.exports},208:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=o(50),r=o.n(a),s=o(49),n=o.n(s),i=o(51),l=o.n(i),c=o(87),d=o(88),m=o(89);t.default={data:function(){return{baseUrl:d.a}},created:function(){this.adminInfo.id||this.getAdminData()},computed:l()({},o.i(m.b)(["adminInfo"])),methods:l()({},o.i(m.c)(["getAdminData"]),{handleCommand:function(e){var t=this;return n()(r.a.mark(function a(){var s;return r.a.wrap(function(a){for(;;)switch(a.prev=a.next){case 0:if("home"!=e){a.next=4;break}t.$router.push("/manage"),a.next=9;break;case 4:if("singout"!=e){a.next=9;break}return a.next=7,o.i(c.b)();case 7:s=a.sent,1==s.status?(t.$message({type:"success",message:"退出成功"}),t.$router.push("/")):t.$message({type:"error",message:s.message});case 9:case"end":return a.stop()}},a,t)}))()}})}},209:function(e,t,o){t=e.exports=o(187)(!1),t.push([e.i,".allcover{position:absolute;top:0;right:0}.ctt{left:50%;transform:translate(-50%,-50%)}.ctt,.tb{position:absolute;top:50%}.tb{transform:translateY(-50%)}.lr{position:absolute;left:50%;transform:translateX(-50%)}.header_container{background-color:#eff2f7;height:60px;display:-ms-flexbox;display:flex;-ms-flex-pack:justify;justify-content:space-between;-ms-flex-align:center;align-items:center;padding-left:20px}.avator{width:36px;height:36px;border-radius:50%;margin-right:37px}.el-dropdown-menu__item{text-align:center}",""])},210:function(e,t,o){var a=o(209);"string"==typeof a&&(a=[[e.i,a,""]]),a.locals&&(e.exports=a.locals);o(188)("02d08ad4",a,!0)},211:function(e,t,o){o(210);var a=o(86)(o(208),o(212),null,null);e.exports=a.exports},212:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",{staticClass:"header_container"},[o("el-breadcrumb",{attrs:{separator:"/"}},[o("el-breadcrumb-item",{attrs:{to:{path:"/manage"}}},[e._v("首页")]),e._v(" "),e._l(e.$route.meta.nabs,function(t,a){return o("el-breadcrumb-item",{key:a},[e._v(e._s(t))])})],2),e._v(" "),o("el-dropdown",{attrs:{"menu-align":"start"},on:{command:e.handleCommand}},[o("img",{staticClass:"avator",attrs:{src:e.baseUrl+e.adminInfo.avatar}}),e._v(" "),o("el-dropdown-menu",{attrs:{slot:"dropdown"},slot:"dropdown"},[o("el-dropdown-item",{attrs:{command:"home"}},[e._v("首页")]),e._v(" "),o("el-dropdown-item",{attrs:{command:"singout"}},[e._v("退出")])],1)],1)],1)},staticRenderFns:[]}},304:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=o(51),r=o.n(a),s=o(50),n=o.n(s),i=o(49),l=o.n(i),c=o(211),d=o.n(c),m=o(87),u=o(88);t.default={data:function(){return{baseUrl:u.a,categoryForm:{categoryList:[],unitList:[{value:1,label:"元/斤"},{value:2,label:"元/瓶"},{value:3,label:"元/袋"},{value:4,label:"元/只"},{value:5,label:"元/个"}],unitValue:"",categorySelect:1,unitSelect:{value:1,label:"元/斤"},name:"",remarks:""},categoryRules:{speciesId:[{required:!0,message:"请选择食品分类",trigger:"blur"}]},foodForm:{name:"",details:"",imgLink:"",speciesId:"",feature:"",price:null,unitId:1},foodRules:{name:[{required:!0,message:"请输入食品名称",trigger:"blur"}],price:[{required:!0,message:"请输入食品价格",trigger:"blur"}]},showAddCategory:!1,dialogFormVisible:!1}},components:{headTop:d.a},created:function(){this.initData()},computed:{selectValue:function(){return this.categoryForm.categoryList[this.categoryForm.categorySelect]||{}}},methods:{initData:function(){var e=this;return l()(n.a.mark(function t(){var a;return n.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return t.prev=0,t.next=3,o.i(m.d)();case 3:a=t.sent,console.log(a.data),200==a.code?(a.data.map(function(e,t){e.index=t,e.value=e.id,e.label=e.name}),e.categoryForm.categoryList=a.data,console.log(e.categoryForm.categoryList)):console.log(a),t.next=11;break;case 8:t.prev=8,t.t0=t.catch(0),console.log(t.t0);case 11:case"end":return t.stop()}},t,e,[[0,8]])}))()},addCategoryFun:function(){this.showAddCategory=!this.showAddCategory},submitCategoryForm:function(e){var t=this;return l()(n.a.mark(function e(){var a,r;return n.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return a={name:t.categoryForm.name,remarks:t.categoryForm.remarks},e.prev=1,e.next=4,o.i(m.s)(a);case 4:r=e.sent,200==r.code?(t.initData(),t.categoryForm.name="",t.categoryForm.remarks="",t.showAddCategory=!1,t.$message({type:"success",message:"添加成功"})):t.$message({type:"error",message:r.message}),e.next=11;break;case 8:e.prev=8,e.t0=e.catch(1),console.log(e.t0);case 11:case"end":return e.stop()}},e,t,[[1,8]])}))()},uploadImg:function(e,t){200==e.code?this.foodForm.imgLink=e.data:this.$message.error("上传图片失败！")},beforeImgUpload:function(e){var t="image/jpeg"===e.type||"image/png"===e.type,o=e.size/1024/1024<2;return t||this.$message.error("上传头像图片只能是 JPG 格式!"),o||this.$message.error("上传头像图片大小不能超过 2MB!"),t&&o},tableRowClassName:function(e,t){return 1===t?"info-row":3===t?"positive-row":""},addFood:function(e,t){var a=this;this.$refs[t].validate(function(){var e=l()(n.a.mark(function e(t){var s,i;return n.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:if(!t){e.next=14;break}return s=r()({},a.foodForm,{speciesId:a.categoryForm.categorySelect}),e.prev=2,e.next=5,o.i(m.v)(s);case 5:i=e.sent,200==i.code?(console.log(i),a.$message({type:"success",message:"添加成功"}),a.foodForm={name:"",details:"",imgLink:"",speciesId:"",feature:"",price:0,unitId:1},a.$router.push("foodList")):a.$message({type:"error",message:i.message}),e.next=12;break;case 9:e.prev=9,e.t0=e.catch(2),console.log(e.t0);case 12:e.next=16;break;case 14:return a.$notify.error({title:"错误",message:"请检查输入是否正确",offset:100}),e.abrupt("return",!1);case 16:case"end":return e.stop()}},e,a,[[2,9]])}));return function(t){return e.apply(this,arguments)}}())}}}},328:function(e,t,o){t=e.exports=o(187)(!1),t.push([e.i,".allcover{position:absolute;top:0;right:0}.ctt{left:50%;transform:translate(-50%,-50%)}.ctt,.tb{position:absolute;top:50%}.tb{transform:translateY(-50%)}.lr{position:absolute;left:50%;transform:translateX(-50%)}.form{min-width:400px;width:100%;margin-bottom:30px}.form:hover{box-shadow:0 0 8px 0 rgba(232,237,250,.6),0 2px 4px 0 rgba(232,237,250,.5);border-radius:6px;transition:all .4s}.food_form{border:1px solid #eaeefb;padding:10px 0}.form_header{text-align:center;margin-bottom:10px}.species_select{width:100%;border:1px solid #eaeefb;padding:10px 30px 10px 10px;border-top-right-radius:6px;border-top-left-radius:6px}.add_category_row{height:0;overflow:hidden;transition:all .4s;background:#f9fafc}.showEdit{height:185px}.add_category{background:#f9fafc;padding:10px 30px 0 10px;border:1px solid #eaeefb;border-top:none}.add_category_button{text-align:center;line-height:40px;border-bottom-right-radius:6px;border-bottom-left-radius:6px;border:1px solid #eaeefb;border-top:none;transition:all .4s}.add_category_button:hover{background:#f9fafc}.add_category_button:hover .edit_icon,.add_category_button:hover span{color:#20a0ff}.add_category_button span{font-size:14px;color:#999;transition:all .4s}.add_category_button .edit_icon{color:#ccc;transition:all .4s}.avatar-uploader .el-upload{border:1px dashed #d9d9d9;border-radius:6px;cursor:pointer;position:relative;overflow:hidden}.avatar-uploader .el-upload:hover{border-color:#20a0ff}.avatar-uploader-icon{font-size:28px;color:#8c939d;width:120px;height:120px;line-height:120px;text-align:center}.avatar{width:120px;height:120px;display:block}.cell{text-align:center}",""])},432:function(e,t,o){var a=o(328);"string"==typeof a&&(a=[[e.i,a,""]]),a.locals&&(e.exports=a.locals);o(188)("f2038b4a",a,!0)},453:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",[o("head-top"),e._v(" "),o("el-row",{staticStyle:{"margin-top":"20px"}},[o("el-col",{attrs:{span:14,offset:4}},[o("header",{staticClass:"form_header"},[e._v("选择食品分类")]),e._v(" "),o("el-form",{ref:"categoryForm",staticClass:"form",attrs:{model:e.categoryForm,rules:e.categoryRules,"label-width":"110px"}},[o("el-row",{staticClass:"species_select"},[o("el-form-item",{attrs:{label:"食品分类",prop:"speciesId"}},[o("el-select",{staticStyle:{width:"100%"},attrs:{placeholder:e.selectValue.label},model:{value:e.categoryForm.categorySelect,callback:function(t){e.$set(e.categoryForm,"categorySelect",t)},expression:"categoryForm.categorySelect"}},e._l(e.categoryForm.categoryList,function(e){return o("el-option",{key:e.index,attrs:{label:e.label,value:e.value}})}))],1)],1),e._v(" "),o("el-row",{staticClass:"add_category_row",class:e.showAddCategory?"showEdit":""},[o("div",{staticClass:"add_category"},[o("el-form-item",{attrs:{label:"食品分类",prop:"name"}},[o("el-input",{model:{value:e.categoryForm.name,callback:function(t){e.$set(e.categoryForm,"name",t)},expression:"categoryForm.name"}})],1),e._v(" "),o("el-form-item",{attrs:{label:"分类描述",prop:"remarks"}},[o("el-input",{model:{value:e.categoryForm.remarks,callback:function(t){e.$set(e.categoryForm,"remarks",t)},expression:"categoryForm.remarks"}})],1),e._v(" "),o("el-form-item",[o("el-button",{attrs:{type:"primary"},on:{click:function(t){e.submitCategoryForm("categoryForm")}}},[e._v("提交")])],1)],1)]),e._v(" "),o("div",{staticClass:"add_category_button",on:{click:e.addCategoryFun}},[e.showAddCategory?o("i",{staticClass:"el-icon-caret-top edit_icon"}):o("i",{staticClass:"el-icon-caret-bottom edit_icon",attrs:{slot:"icon"},slot:"icon"}),e._v(" "),o("span",[e._v("添加食品分类")])])],1),e._v(" "),o("header",{staticClass:"form_header"},[e._v("添加食品")]),e._v(" "),o("el-form",{ref:"foodForm",staticClass:"form food_form",attrs:{model:e.foodForm,rules:e.foodRules,"label-width":"110px"}},[o("el-form-item",{attrs:{label:"食品名称",prop:"name"}},[o("el-input",{model:{value:e.foodForm.name,callback:function(t){e.$set(e.foodForm,"name",t)},expression:"foodForm.name"}})],1),e._v(" "),o("el-form-item",{attrs:{label:"食品价格",prop:"price"}},[o("el-input",{attrs:{placeholder:"食品价格参考：5.00"},model:{value:e.foodForm.price,callback:function(t){e.$set(e.foodForm,"price",t)},expression:"foodForm.price"}},[o("el-select",{staticStyle:{width:"100px"},attrs:{slot:"append",placeholder:e.categoryForm.unitSelect.label},slot:"append",model:{value:e.foodForm.unitId,callback:function(t){e.$set(e.foodForm,"unitId",t)},expression:"foodForm.unitId"}},e._l(e.categoryForm.unitList,function(e){return o("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1)],1),e._v(" "),o("el-form-item",{attrs:{label:"食品详情",prop:"details"}},[o("el-input",{model:{value:e.foodForm.details,callback:function(t){e.$set(e.foodForm,"details",t)},expression:"foodForm.details"}})],1),e._v(" "),o("el-form-item",{attrs:{label:"上传食品图片"}},[o("el-upload",{staticClass:"avatar-uploader",attrs:{action:e.baseUrl+"/api/upload/img","show-file-list":!1,"on-success":e.uploadImg,"before-upload":e.beforeImgUpload,name:"imgFile"}},[e.foodForm.imgLink?o("img",{staticClass:"avatar",attrs:{src:e.foodForm.imgLink}}):o("i",{staticClass:"el-icon-plus avatar-uploader-icon"})])],1),e._v(" "),o("el-form-item",[o("el-button",{attrs:{type:"primary"},on:{click:function(t){e.addFood("categoryForm","foodForm")}}},[e._v("确认添加食品")])],1)],1)],1)],1)],1)},staticRenderFns:[]}}});