<template>
    <div>
        <head-top></head-top>
        <el-row style="margin-top: 20px;">
            <el-col :span="14" :offset="2">
                <header class="form_header">添加配送分单</header>
                <el-form :model="subOrderForm" :rules="subOrderRules" ref="subOrderForm" label-width="110px"
                         class="form food_form">
                    <el-form-item label="分单名称" prop="name">
                        <el-input v-model="subOrderForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="客户" prop="customerId">
                        <el-select v-model="subOrderForm.customerId" :placeholder="customerItem.label"
                                   @change="chooseCustomer(subOrderForm.customerId)" style="width: 100%;">
                            <el-option
                                v-for="item in customerList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item v-show="disable">
                        <template>
                            <div>
                                <el-radio-group v-model="custRadio"  @change="chooseCustomer(subOrderForm.customerId)" size="mini">
                                    <el-radio label="1" border>默认客户商品单</el-radio>
                                    <el-radio label="2" border>上一子单商品</el-radio>
                                </el-radio-group>
                            </div>
                        </template>
                    </el-form-item>
                    <el-form-item label="分单备注" prop="remarks">
                        <el-input v-model="subOrderForm.remarks"></el-input>
                    </el-form-item>
                </el-form>
                <header class="form_header">选择食品</header>
                <el-dialog title="选择食品信息" v-model="dialogFormVisible">
                    <div class="demo-input-size">
                        <el-row class="category_select2">
                            <el-input v-model="queryForm.name" placeholder="食品名称" style="width:200px;"></el-input>
                            <el-select v-model="queryForm.speciesId" placeholder="请选择食品分类" style="width:200px;">
                                <el-option
                                    v-for="item in menuSelectOptions"
                                    :key="item.value + 'C'"
                                    :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                            <el-button @click="handleQuery" type="primary">查询</el-button>
                            <el-button @click="handleSelection" type="primary">确认</el-button>
                        </el-row>
                    </div>
                    <el-table
                        :data="tableData"
                        :row-key="row => row.index"
                        style="width: 100%"
                        @selection-change="handleSelectionChange">
                        <el-table-column
                            type="selection"
                            width="55">
                        </el-table-column>
                        <el-table-column
                            label="图片"
                            prop="imgLink">
                            <template slot-scope="scope">
                                <img :src="scope.row.imgLink" alt="" style="width: 50px;height: 50px">
                            </template>
                        </el-table-column>
                        <el-table-column
                            label="食品名称"
                            prop="name">
                        </el-table-column>
                        <el-table-column
                            label="食品价格"
                            prop="price">
                            <template slot-scope="scope">
                                <label>{{scope.row.price + ' ' + scope.row.unitName}}</label>
                            </template>
                        </el-table-column>
                        <el-table-column
                            label="食品种类"
                            prop="speciesName">
                        </el-table-column>
                    </el-table>
                    <div class="Pagination">
                        <el-pagination
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange"
                            :current-page="currentPage"
                            :page-size="10"
                            layout="total, prev, pager, next"
                            :total="count">
                        </el-pagination>
                    </div>
                </el-dialog>
                <div class="add_Food_button" @click="addFoodFun">
                    <span>添加食品信息</span>
                </div>
                <el-table
                    :data="tableFoodData"
                    :row-key="row => row.index"
                    style="width: 100%">
                    <el-table-column
                        type="index"
                        label="序号"
                        width="80">
                    </el-table-column>
                    <!--<el-table-column-->
                    <!--label="图片"-->
                    <!--prop="imgLink" width="80">-->
                    <!--<template slot-scope="scope">-->
                    <!--<img :src="scope.row.imgLink" alt="" style="width: 50px;height: 50px">-->
                    <!--</template>-->
                    <!--</el-table-column>-->
                    <el-table-column
                        label="名称"
                        prop="name">
                    </el-table-column>
                    <el-table-column
                        label="数量"
                        prop="number" width="120px;">
                        <template slot-scope="scope">
                            <el-input-number v-model="scope.row.number" :controls="controls" size="small" :min="1"
                                             :max="10000000"></el-input-number>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="单价"
                        prop="unitPrice" width="180px;">
                        <template slot-scope="scope">
                            <el-input-number v-model="scope.row.unitPrice" :controls="controls" size="small" :min="1"
                                             :max="10000000"></el-input-number>
                            <label>{{scope.row.unitName}}</label>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="小计（元）"
                        prop="sum" width="120">
                        <template slot-scope="scope">
                            <label>{{scope.row.number * scope.row.unitPrice}}</label>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="80">
                        <template scope="scope">
                            <el-button
                                size="small"
                                type="danger"
                                @click="handleDelete(scope.$index, scope.row)">删除
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div align="center" style="margin:20px 10px;width: 650px;">
                    <el-button type="primary" @click="addSubOrder('subOrderForm')">保存</el-button>
                    <el-button type="primary" @click="cancel()">取消</el-button>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import headTop from '@/components/headTop'
    import {getCustomers, getFoods, getSpecies, addSubOrder, getSubOrder, getSubByCsIdOrder,listCustFood} from '@/api/getData'
    import {baseUrl} from '@/config/env'
    export default {
        data(){
            return {
                baseUrl,
                limit: 10,
                count: 0,
                tableData: [],
                tableItem: {},
                tableFoodData: [],
                currentPage: 1,
                disable:true,
                custRadio: '1',
                queryForm: {
                    speciesId: '',
                    name: ''
                },
                customerList: [],
                customerItem: {},
                showAddFood: false,
                subOrderForm: {
                    id: null,
                    name: '',
                    customerId: '',
                    remarks: '',
                    mainOrderId: null
                },
                subOrderRules: {
                    name: [
                        {required: true, message: '请输入子单名称', trigger: 'blur'},
                    ],
                    customerId: [
                        {type: 'number', required: true, message: '请选择客户', trigger: 'blur'},
                    ]
                },
                unitList: [{
                    value: 1,
                    label: '元/斤'
                }, {
                    value: 2,
                    label: '元/瓶'
                }, {
                    value: 3,
                    label: '元/袋'
                }, {
                    value: 4,
                    label: '元/只'
                }, {
                    value: 5,
                    label: '元/个'
                }, {
                    value: 6,
                    label: '元/板'
                }, {
                    value: 7,
                    label: '元/包'
                }, {
                    value: 8,
                    label: '元/块'
                }, {
                    value: 9,
                    label: '元/件'
                }, {
                    value: 10,
                    label: '元/箱'
                }],
                showAddCategory: false,
                multipleSelection: [],
                menuOptions: [],
                controls: false,
                menuSelectOptions: [],
                dialogFormVisible: false
            }
        },
        components: {
            headTop,
        },
        created(){
        },
        activated() {
            this.initData();
        },
        computed: {},
        methods: {
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.getFoods()
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            handleDelete(index, row){
                this.tableFoodData.splice(index, 1);
            },
            async chooseCustomer(customerId){
                if(customerId == ''){
                    return false;
                }
                //如若为新增，则联想到客户最近下单数据填充食品列表
                if (this.subOrderForm.id == null) {
                    if (this.custRadio == '1') {
                        try{
                            const result = await listCustFood({
                                customerId: customerId
                            });
                            if (result.code == 200) {
                                const data = result.data;
                                if (data == null) {
                                    return false;
                                }
                                this.tableFoodData = [];
                                data.forEach((item, index) => {
                                    this.tableFoodData.push({
                                        name: item.foodName,
                                        mainOrderId: this.subOrderForm.mainOrderId,
                                        foodId: item.foodId,
                                        number: item.number,
                                        costPrice:item.costPrice,
                                        unitPrice: item.unitPrice,
                                        unitName: this.unitList[item.unitId - 1].label
                                    });
                                })
                            } else {
                                console.log(result)
                            }
                        } catch (err) {
                            console.log(err)
                        }
                    } else {
                        try {
                            const result = await getSubByCsIdOrder({
                                customerId: customerId
                            });
                            if (result.code == 200) {
                                const data = result.data;
                                if (data == null) {
                                    return false;
                                }
                                this.subOrderForm = {
                                    id: data.id,
                                    name: data.name,
                                    customerId: data.customerId,
                                    remarks: data.remarks,
                                    mainOrderId: this.subOrderForm.mainOrderId
                                };
                                this.tableFoodData = [];
                                var mainOrderId =  this.subOrderForm.mainOrderId;
                                data.list.forEach((item, index) => {
                                    this.tableFoodData.push({
                                        name: item.name,
                                        mainOrderId: mainOrderId,
                                        foodId: item.foodId,
                                        number: item.number,
                                        costPrice:item.costPrice,
                                        unitPrice: item.unitPrice,
                                        unitName: this.unitList[item.unitId - 1].label
                                    });
                                })
                            } else {
                                console.log(result)
                            }
                        } catch (err) {
                            console.log(err)
                        }
                    }
                }else {
                    this.disable = false;
                }
            },
            handleSelection(){
                const selectIds = [];
                const existIds = [];
                if (this.multipleSelection == 0) {
                    this.$message({
                        type: 'warning',
                        message: '请选择食品信息'
                    });
                    return false;
                }
                if (this.tableFoodData.length === 0) {
                    this.multipleSelection.forEach((item, index) => {
                        const tableData = {};
                        tableData.foodId = item.id;
                        tableData.mainOrderId = this.subOrderForm.mainOrderId;
                        tableData.name = item.name;
                        tableData.speciesId = item.speciesId;
                        tableData.speciesName = item.speciesName;
                        tableData.details = item.details;
                        tableData.imgLink = item.imgLink;
                        tableData.unitPrice = item.price;
                        tableData.costPrice = item.costPrice;
                        tableData.unitId = item.unitId;
                        tableData.unitName = this.unitList[item.unitId - 1].label;
                        this.tableFoodData.push(tableData);
                    });
                } else {
                    this.multipleSelection.forEach((item, index) => {
                        selectIds.push(item.id);
                    });
                    this.tableFoodData.forEach((item, index) => {
                        if (selectIds.indexOf(item.foodId) >= 0) {
                            existIds.push(item.foodId);
                        }
                    });
                    this.multipleSelection.forEach((item, index) => {
                        if (existIds.indexOf(item.id) < 0) {
                            const tableData = {};
                            tableData.foodId = item.id;
                            tableData.mainOrderId = this.subOrderForm.mainOrderId;
                            tableData.name = item.name;
                            tableData.speciesId = item.speciesId;
                            tableData.speciesName = item.speciesName;
                            tableData.details = item.details;
                            tableData.imgLink = item.imgLink;
                            tableData.unitPrice = item.price;
                            tableData.costPrice = item.costPrice;
                            tableData.unitId = item.unitId;
                            tableData.unitName = this.unitList[item.unitId - 1].label;
                            this.tableFoodData.push(tableData);
                        }
                    });
                }
                this.dialogFormVisible = !this.dialogFormVisible;
            },
            cancel(){
                this.menuSelectOptions = [];
                this.tableFoodData = [];
                this.$router.push({path: 'subOrderList', query: {mainOrderId: this.subOrderForm.mainOrderId}});
            },
            handleQuery(){
                this.getFoods();
            },
            async initData(){
                this.subOrderForm = {
                    id: null,
                    name: '',
                    customerId: '',
                    remarks: '',
                    mainOrderId: null
                };
                this.tableFoodData = [];
                this.disable = true;
                this.getCustomers();
                this.getMenu();
                this.getFoods();
                this.tableFoodData = [];
                this.subOrderForm.mainOrderId = this.$route.query.mainOrderId;
                if (typeof this.$route.query.subOrderId == 'undefined') {
                    return false;
                }
                if (typeof this.$route.query.subOrderId != 'undefined') {
                    this.subOrderForm.id = this.$route.query.subOrderId;
                    this.getSubOrder();
                }
            },
            async getMenu(){
                this.menuOptions = [];
                try {
                    const menu = await getSpecies();
                    if (menu.code = 200) {
                        this.menuSelectOptions.push({
                            label: '请选择分类',
                            value: '',
                        });
                        menu.data.forEach((item, index) => {
                            this.menuOptions.push({
                                label: item.name,
                                value: item.id,
                                index: index
                            });
                            this.menuSelectOptions.push({
                                label: item.name,
                                value: item.id,
                                index: index
                            });
                        })
                    } else {
                        console.log(menu.message);
                    }
                } catch (err) {
                    console.log('获取食品分类失败', err);
                }
            },
            async getSubOrder(){
                try {
                    const result = await getSubOrder({
                        subOrderId: this.subOrderForm.id
                    });
                    if (result.code == 200) {
                        const data = result.data;
                        this.subOrderForm = {
                            id: data.id,
                            name: data.name,
                            customerId: data.customerId,
                            remarks: data.remarks,
                            mainOrderId: data.mainOrderId
                        };
                        data.list.forEach((item, index) => {
                            this.tableFoodData.push({
                                id: item.id,
                                name: item.name,
                                mainOrderId: item.mainOrderId,
                                subOrderId: item.subOrderId,
                                foodId: item.foodId,
                                number: item.number,
                                costPrice: item.costPrice,
                                unitPrice: item.unitPrice,
                                unitName: this.unitList[item.unitId - 1].label
                            });
                        })
                    } else {
                        console.log(result)
                    }
                } catch (err) {
                    console.log(err)
                }
            },
            async getCustomers(){
                try {
                    const result = await getCustomers();
                    if (result.code == 200) {
                        result.data.map((item, index) => {
                            item.index = index;
                            item.value = item.id;
                            item.label = item.name;
                        })
                        this.customerList = result.data;
                    } else {
                        console.log(result)
                    }
                } catch (err) {
                    console.log(err)
                }
            },
            async getFoods(){
                const Foods = await getFoods({
                    pageNo: this.currentPage,
                    pageSize: this.limit,
                    speciesId: this.queryForm.speciesId,
                    mainOrderId: this.subOrderForm.mainOrderId,
                    name: this.queryForm.name
                });
                if (Foods.code == 200) {
                    this.count = Foods.data.total;
                    this.tableData = [];
                    Foods.data.list.forEach((item, index) => {
                        const tableData = {};
                        tableData.id = item.id;
                        tableData.name = item.name;
                        tableData.speciesId = item.speciesId;
                        tableData.speciesName = item.speciesName;
                        tableData.details = item.details;
                        tableData.imgLink = item.imgLink;
                        tableData.price = item.price;
                        tableData.unitId = item.unitId;
                        tableData.unitName = this.unitList[item.unitId - 1].label;
                        tableData.index = index;
                        this.tableData.push(tableData);
                    })
                } else {
                    throw new Error(Foods.message);
                }
            },
            addFoodFun(){
                this.dialogFormVisible = !this.dialogFormVisible;
            },
            addSubOrder(subOrderForm){
                this.$refs[subOrderForm].validate(async(valid) => {
                    if (valid) {
                        if (this.tableFoodData.length === 0) {
                            this.$message({
                                type: 'warning',
                                message: '请选择食品信息'
                            });
                            return false;
                        }
                        const params = {
                            ...this.subOrderForm,
                            list: this.tableFoodData
                        }
                        try {
                            const result = await addSubOrder(params);
                            if (result.code == 200) {
                                console.log(result)
                                this.$message({
                                    type: 'success',
                                    message: '添加成功'
                                });
                                this.$router.push({
                                    path: 'subOrderList',
                                    query: {mainOrderId: this.subOrderForm.mainOrderId}
                                });
                                this.menuSelectOptions = [];
                                this.subOrderForm = {
                                    name: '',
                                    details: '',
                                    imgLink: '',
                                    speciesId: '',
                                    feature: '',
                                    price: 0.00,
                                    unitId: 1
                                }
                            } else {
                                this.$message({
                                    type: 'error',
                                    message: result.message
                                });
                            }
                        } catch (err) {
                            console.log(err)
                        }
                    } else {
                        this.$notify.error({
                            title: '错误',
                            message: '请检查输入是否正确',
                            offset: 100
                        });
                        return false;
                    }
                });
            }
        }
    }
</script>

<style lang="less">
    @import '../../style/mixin';

    .form{
        min-width: 650px;
        width: 100%;
        margin-bottom: 30px;
    &:hover{
         box-shadow: 0 0 8px 0 rgba(232,237,250,.6), 0 2px 4px 0 rgba(232,237,250,.5);
         border-radius: 6px;
         transition: all 400ms;
     }
    }
    .food_form{
        border: 1px solid #eaeefb;
        padding: 10px 0px;
    }

    .el-dialog--small {
        min-width: 680px;
    }

    .el-table {
        overflow: hidden;
        width: 100%;
        min-width: 650px;
        max-width: 100%;
        background-color: #fff;
        border: 1px solid #dfe6ec;
        font-size: 12px;
        color: #1f2d3d;
    }
    .form_header{
        text-align: center;
        margin-bottom: 10px;
        min-width: 650px;
        width: 100%;
    }
    .species_select{
        width: 100%;
        border: 1px solid #eaeefb;
        padding: 10px 30px 10px 10px;
        border-top-right-radius: 6px;
        border-top-left-radius: 6px;
    }
    .add_category_row{
        height: 0;
        overflow: hidden;
        transition: all 400ms;
        background: #f9fafc;
    }
    .el-input-number{
        display: inline-block;
        width: 80px;
        position: relative;
        line-height: normal;
    }
    .showEdit{
        height: 185px;
    }
    .add_category{
        background: #f9fafc;
        padding: 10px 30px 0px 10px;
        border: 1px solid #eaeefb;
        border-top: none;
    }
    .species_select{
        width: 100%;
        border: 1px solid #eaeefb;
        padding: 10px 30px 10px 10px;
        border-top-right-radius: 6px;
        border-top-left-radius: 6px;
    }
    .add_Food_button{
        min-width: 650px;
        width: 100%;
        text-align: center;
        line-height: 40px;
        border-bottom-right-radius: 6px;
        border-bottom-left-radius: 6px;
        border: 1px solid #eaeefb;
        border-top: none;
        transition: all 400ms;
        cursor:pointer;
    &:hover{
         background: #f9fafc;
    span, .edit_icon{
        color: #20a0ff;
    }
    }
    span{
    .sc(14px, #999);
        transition: all 400ms;
    }
    .edit_icon{
        color: #ccc;
        transition: all 400ms;
    }
    }
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #20a0ff;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 120px;
        height: 120px;
        line-height: 120px;
        text-align: center;
    }
    .avatar {
        width: 120px;
        height: 120px;
        display: block;
    }
    .cell{
        text-align: center;
    }
</style>
