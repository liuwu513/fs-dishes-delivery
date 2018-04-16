<template>
    <div class="fillcontain">
        <head-top></head-top>
        <div class="table_container">
            <div class="demo-input-size">
                <el-row class="category_select3">
                    <el-input v-model="queryForm.name" placeholder="子单号/名称" style="width:200px;"></el-input>
                    <el-date-picker
                        v-model="queryForm.startTime"
                        type="datetime"
                        format="yyyy-MM-dd HH:mm:ss"
                        placeholder="选择开始日期">
                    </el-date-picker>
                    <el-date-picker
                        v-model="queryForm.endTime"
                        type="datetime"
                        format="yyyy-MM-dd HH:mm:ss"
                        placeholder="选择结束日期">
                    </el-date-picker>
                    <el-button @click="handleQuery" type="primary">查询</el-button>
                </el-row>
            </div>
            <div style="margin-bottom: 10px">
                <el-button @click="handleAdd" type="primary">新增</el-button>
                <el-button @click="handlePayment(3)" type="primary">付款</el-button>
                <el-button @click="handlePayment(2)" type="primary">撤销付款</el-button>
            </div>
            <el-table
                :data="tableData"
                style="width: 100%"
                @selection-change="handleSelectionChange">
                <el-table-column
                    type="selection"
                    width="55">
                </el-table-column>
                <el-table-column
                    property="id"
                    label="子单号"
                    width="155">
                </el-table-column>
                <el-table-column
                    property="name"
                    label="子单名称">
                </el-table-column>
                <el-table-column
                    property="customerName"
                    label="客户名称">
                </el-table-column>
                <el-table-column
                    property="totalAmount"
                    label="总金额(元)">
                </el-table-column>
                <el-table-column
                    property="discountAmount"
                    label="总优惠金额">
                </el-table-column>
                <el-table-column
                    property="createTime"
                    label="创建时间"
                    :formatter="dateFormat">
                </el-table-column>
                <el-table-column
                    property="payStatus"
                    label="付款状态"
                    :formatter="formatterPayStatus">
                </el-table-column>
                <el-table-column label="操作" width="250">
                    <template scope="scope">
                        <el-button
                            size="small"
                            @click="handleEdit(scope.row)">编辑
                        </el-button>
                        <el-button
                            size="small"
                            @click="handleDetails(scope.row)">查看详情
                        </el-button>
                        <el-button
                            size="small"
                            type="danger"
                            @click="handleDelete(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="Pagination" style="text-align: left;margin-top: 10px;">
                <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-size="10"
                    layout="total, prev, pager, next"
                    :total="count">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
    import headTop from '../../components/headTop'
    import {moment} from '@/config/moment'
    import {listSubOrder,addSubOrder,deleteSubOrders,paymentSubOrder} from '@/api/getData'
    export default {
        data(){
            return {
                tableData: [],
                currentRow: null,
                limit: 10,
                count: 0,
                currentPage: 1,
                dialogFormVisible: false,
                name:null,
                queryForm:{
                    name:'',
                    startTime:'',
                    endTime:''
                },
                modalTitle:'',
                selectValue:{
                },
                customerList:{

                },
                selectTable:{
                    totalAmount: 0.00,
                    discountAmount: 0.00
                },
                mainOrderId:null,
                subOrderIds:[],
                multipleSelection:[],
            }
        },
        components: {
            headTop,
        },
        created(){
            this.initData();
        },
        methods: {
            formatterPayStatus(row, column){
                var statusDesc = "";
                switch (row.payStatus){
                    case 1:
                        statusDesc = "未付款";
                        break;
                    case 2:
                        statusDesc = "付款中";
                        break;
                    case 3:
                        statusDesc = "已付款";
                    break;
                }
                return statusDesc;
            },
            //时间格式化
            dateFormat:function(row, column) {
                var date = row[column.property];
                if (date == undefined) {
                    return "";
                }
                return moment(date).format("YYYY-MM-DD HH:mm:ss");
            },
            async initData(){
                try {
                    this.mainOrderId = this.$route.query.mainOrderId;
                    this.getSubOrder();
                } catch (err) {
                    console.log('获取数据失败', err);
                }
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.getSubOrder()
            },
            handleQuery(){
                this.initData();
            },
            handleAdd(){
                this.$router.push({ path: 'addSubOrder', query: { mainOrderId : this.mainOrderId }});
            },
            async handlePayment(payStatus){
                try {
                    if (this.multipleSelection.length <= 0){
                        this.$message({
                            type: 'warning',
                            message: '请选择分单数据！'
                        });
                        return false;
                    }
                    this.subOrderIds = [];
                    this.multipleSelection.forEach((item, index) => {
                        this.subOrderIds.push(item.id);
                    });
                    const res = await paymentSubOrder({
                        mainOrderId:this.mainOrderId,
                        subOrderIds: this.subOrderIds,
                        payStatus: payStatus
                    });
                    if (res.code == 200) {
                        this.$message({
                            type: 'success',
                            message: '更改付款状态成功'
                        });
                        this.initData();
                    } else {
                        throw new Error(res.message)
                    }
                    this.multipleSelection = [];
                    this.subOrderIds = [];
                } catch (err) {
                    this.$message({
                        type: 'error',
                        message: err.message
                    });
                    console.log('更改付款状态失败')
                }
            },
            handleEdit(row){
                this.$router.push({ path: 'addSubOrder', query: { sku: Math.random(),mainOrderId : this.mainOrderId,subOrderId: row.id }});
            },
            handleDetails(row){

            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            async getSelectItemData(row){
                this.selectTable = row;
                this.tableData.splice(row.index, 1, {...this.selectTable});
            },
            async handleDelete(index, row) {
                try {
                    this.subOrderIds.push(row.id);
                    const res = await deleteSubOrders({
                        subOrderIds: this.subOrderIds
                    });
                    if (res.code == 200) {
                        this.$message({
                            type: 'success',
                            message: '删除子单成功'
                        });
                        this.tableData.splice(index, 1);
                    } else {
                        throw new Error(res.message)
                    }
                    this.subOrderIds = [];
                } catch (err) {
                    this.$message({
                        type: 'error',
                        message: err.message
                    });
                    console.log('删除主单失败')
                }
            },
            async getSubOrder(){
                try {
                    const res = await listSubOrder({
                            pageNo: this.currentPage,
                            pageSize: this.limit,
                            mainOrderId: this.mainOrderId,
                            name:this.queryForm.name,
                            startTime: this.queryForm.startTime,
                            endTime: this.queryForm.endTime
                        });
                    if (res.code == 200) {
                        this.count = res.data.total;
                        this.tableData = [];
                        res.data.list.forEach((item,index) => {
                            const tableItem = {
                                id: item.id,
                                name: item.name,
                                customerName: item.customerName,
                                totalAmount: item.totalAmount,
                                discountAmount: item.discountAmount,
                                createTime: item.createTime,
                                details: item.details,
                                payStatus: item.payStatus,
                                index: index
                            }
                            this.tableData.push(tableItem);
                        })
                        console.log(this.tableData);
                    } else {
                        throw new Error(res.message)
                    }
                } catch (err) {
                    console.log('获取主单数据失败', err);
                }
            }
        },
    }
</script>

<style lang="less">
    @import '../../style/mixin';

    .table_container {
        padding: 20px;
    }
    .category_select3{
        width: 750px;
        border: 1px solid #eaeefb;
        padding: 10px 30px 10px 10px;
        border-top-right-radius: 6px;
        border-top-left-radius: 6px;
    }
</style>


