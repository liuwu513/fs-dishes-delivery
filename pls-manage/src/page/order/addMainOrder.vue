<template>
    <div>
        <head-top></head-top>
        <el-row style="margin-top: 20px;">
            <el-col :span="14" :offset="1">
                <header class="form_header">配送主单</header>
                <el-form :model="mainOrderForm" :rules="mainOrderRules" ref="mainOrderForm" label-width="110px"
                         class="form food_form">
                    <el-form-item label="主单名称" prop="orderDesc">
                        <el-input v-model="mainOrderForm.orderDesc"></el-input>
                    </el-form-item>
                    <el-form-item label="总金额">
                        <el-input v-model="mainOrderForm.totalAmount" readonly></el-input>
                    </el-form-item>
                    <el-form-item label="优惠金额">
                        <el-input v-model="mainOrderForm.discountAmount" readonly></el-input>
                    </el-form-item>
                    <el-form-item label="备注" prop="details">
                        <el-input v-model="mainOrderForm.details"></el-input>
                    </el-form-item>
                </el-form>
                <header class="form_header">选择食品</header>
                <div>
                    <template>
                        <div>
                            <el-radio-group v-model="mainOrderForm.priceRadio" @change="choosePrice()" size="mini">
                                <el-radio label="1" border>仅调整成本价</el-radio>
                                <el-radio label="2" border>调整成本价与配送价</el-radio>
                            </el-radio-group>
                        </div>
                    </template>
                </div>
                <el-table
                    :data="tableFoodData"
                    :row-key="row => row.index"
                    style="width: 100%">
                    <el-table-column
                        type="index"
                        label="序号"
                        width="75">
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
                        prop="name" min-width="100px">
                    </el-table-column>
                    <el-table-column
                        label="数量"
                        prop="number" min-width="100px;">
                        <template slot-scope="scope">
                            <el-input-number v-model="scope.row.number" :disabled="disable" :controls="controls" size="small" :min="1"
                                             :max="10000000"></el-input-number>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="成本价"
                        prop="costPrice" min-width="150px">
                        <template slot-scope="scope">
                            <el-input-number v-model="scope.row.costPrice" :controls="controls" size="small" :min="0"
                                             :max="10000000"></el-input-number>
                            <label>{{scope.row.unitName}}</label>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="成本小计（元）"
                        prop="sum" min-width="120px;">
                        <template slot-scope="scope">
                            <label>{{scope.row.number * scope.row.costPrice}}</label>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="单价"
                        prop="unitPrice" min-width="150px">
                        <template slot-scope="scope">
                            <el-input-number v-model="scope.row.unitPrice" :disabled="salePriceDisable" :controls="controls" size="small" :min="1"
                                             :max="10000000"></el-input-number>
                            <label>{{scope.row.unitName}}</label>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="出售小计（元）"
                        prop="sum" min-width="120px">
                        <template slot-scope="scope">
                            <label>{{scope.row.number * scope.row.unitPrice}}</label>
                        </template>
                    </el-table-column>
                </el-table>
                <div align="center" style="margin:20px 10px;width: 800px;">
                    <el-button type="primary" @click="addMainOrder('mainOrderForm')">保存</el-button>
                    <el-button type="primary" @click="cancel()">取消</el-button>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import headTop from '@/components/headTop'
    import {addMainOrder, getMainOrder} from '@/api/getData'
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
                salePriceDisable:true,
                mainOrderForm: {
                    id: null,
                    orderDesc: '',
                    details: '',
                    priceRadio:'1',
                    list: []
                },
                mainOrderRules: {
                    orderDesc: [
                        {required: true, message: '请输入主单名称', trigger: 'blur'},
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
                controls: false
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
            handleDelete(index, row){
                this.tableFoodData.splice(index, 1);
            },
            choosePrice(){
              if (this.mainOrderForm.priceRadio == '1'){
                  this.salePriceDisable = true;
              }else {
                  this.salePriceDisable = false;
              }
            },
            cancel(){
                this.tableFoodData = [];
                this.$router.push({path: 'orderList'});
            },
            async initData(){
                this.mainOrderForm = {
                    id: null,
                    orderDesc: '',
                    details: '',
                    priceRadio:'1',
                    list: []
                };
                this.tableFoodData = [];
                this.mainOrderForm.id = this.$route.query.mainOrderId;
                if (typeof this.mainOrderForm.id == 'undefined') {
                    return false;
                }
                if (typeof this.mainOrderForm.id != 'undefined') {
                    this.getMainOrder();
                }
            },
            async getMainOrder(){
                try {
                    const result = await getMainOrder({
                        mainOrderId: this.mainOrderForm.id
                    });
                    if (result.code == 200) {
                        const data = result.data;
                        this.mainOrderForm = {
                            id: data.id,
                            orderDesc: data.orderDesc,
                            details: data.details,
                            priceRadio: '1',
                            totalAmount: data.totalAmount,
                            discountAmount: data.discountAmount
                        };
                        data.list.forEach((item, index) => {
                            if(item.costPrice == 0){
                                item.costPrice = item.unitPrice;
                            }
                            this.tableFoodData.push({
                                id: item.id,
                                name: item.name,
                                mainOrderId: item.mainOrderId,
                                subOrderId: item.subOrderId,
                                costPrice:item.costPrice,
                                foodId: item.foodId,
                                number: item.number,
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
                    mainOrderId: this.mainOrderForm.mainOrderId,
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
            addMainOrder(mainOrderForm){
                this.$refs[mainOrderForm].validate(async(valid) => {
                    if (valid) {
                        if (this.tableFoodData.length === 0) {
                            this.$message({
                                type: 'warning',
                                message: '请选择食品信息'
                            });
                            return false;
                        }
                        const params = {
                            ...this.mainOrderForm,
                            list: this.tableFoodData
                        }
                        try {
                            const result = await addMainOrder(params);
                            if (result.code == 200) {
                                console.log(result)
                                this.$message({
                                    type: 'success',
                                    message: '添加成功'
                                });
                                this.$router.push({
                                    path: 'orderList'
                                });
                                this.mainOrderForm = {
                                    orderDesc: '',
                                    details: '',
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
        min-width: 800px;
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
        min-width: 800px;
        max-width: 100%;
        background-color: #fff;
        border: 1px solid #dfe6ec;
        font-size: 12px;
        color: #1f2d3d;
    }
    .form_header{
        text-align: center;
        margin-bottom: 10px;
        min-width: 800px;
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
        min-width: 800px;
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
