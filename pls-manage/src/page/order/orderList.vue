<template>
    <div class="fillcontain">
        <head-top></head-top>
        <div class="table_container">
            <div class="demo-input-size">
                <el-row class="category_select3">
                    <el-input v-model="queryForm.name" placeholder="主单号/名称" style="width:200px;"></el-input>
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
            </div>
            <el-table
                :data="tableData"
                style="overflow: auto; width: 100%"
                @selection-change="handleSelectionChange">
                <el-table-column
                    type="selection"
                    width="50">
                </el-table-column>
                <el-table-column
                    property="id"
                    label="主订单号"
                    width="125">
                    <template scope="scope">
                        <a :href="baseUrl + viewLink + scope.row.id + token"  target="_blank">
                            <el-tag size="medium">{{ scope.row.id }}</el-tag>
                        </a>
                    </template>
                </el-table-column>
                <el-table-column
                    property="orderDesc"
                    label="主单名称" minWidth="110">
                </el-table-column>
                <el-table-column
                    property="totalAmount"
                    label="总金额" minWidth="85">
                </el-table-column>
                <el-table-column
                    property="discountAmount"
                    label="优惠金额" minWidth="85">
                </el-table-column>
                <el-table-column
                    property="createTime"
                    label="创建时间"
                    :formatter="dateFormat" minWidth="90">
                </el-table-column>
                <el-table-column
                    property="payStatus"
                    label="付款状态"
                    :formatter="formatterPayStatus" minWidth="85">
                </el-table-column>
                <el-table-column label="操作" minWidth="200">
                    <template scope="scope">
                        <el-button
                            size="small"
                            @click="handleEdit(scope.row,'edit')">编辑
                        </el-button>
                        <el-button
                            size="small"
                            @click="handleSub(scope.row)">分单
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
            <el-dialog :title="modalTitle" v-model="dialogFormVisible">
                <el-form :model="selectTable">
                    <el-form-item label="主单名称" label-width="100px">
                        <el-input v-model="selectTable.orderDesc" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="总金额" label-width="100px">
                        <el-input v-model="selectTable.totalAmount" readonly></el-input>
                    </el-form-item>
                    <el-form-item label="优惠金额" label-width="100px">
                        <el-input v-model="selectTable.discountAmount" readonly></el-input>
                    </el-form-item>
                    <el-form-item label="备注" label-width="100px">
                        <el-input v-model="selectTable.details"></el-input>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="updateMainOrder">确 定</el-button>
                </div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import headTop from '../../components/headTop'
    import {moment} from '@/config/moment'
    import {baseUrl} from '@/config/env'
    import {setStore, getStore, removeStore} from '@/config/mUtils'
    import {listMainOrder,addMainOrder,deleteMainOrders} from '@/api/getData'
    export default {
        data(){
            return {
                baseUrl,
                token: '?token='+ getStore('token'),
                viewLink: '/api/rpt/viewMainHtml/',
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
                selectTable:{
                    totalAmount: 0.00,
                    discountAmount: 0.00
                },
                mainOrderIds:[],
                multipleSelection:[],
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
                    this.getMainOrder();
                } catch (err) {
                    console.log('获取数据失败', err);
                }
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.getMainOrder()
            },
            handleEdit(row,type){
                this.getSelectItemData(row);
                this.dialogFormVisible = true;
                if(type == 'edit'){
                    this.modalTitle = '编辑主单信息';
                }else {
                    this.modalTitle = '新增主单信息';
                }
            },
            handleSub(row){
                this.$router.push({ path: 'subOrderList', query: { mainOrderId : row.id }});
            },
            handleQuery(){
                this.initData();
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            handleAdd(){
                this.selectTable = {
                    id: null,
                    totalAmount: 0.00,
                    discountAmount: 0.00,
                    details: null
                }
                this.modalTitle = '新增主单信息';
                this.dialogFormVisible = true;
            },
            async getSelectItemData(row){
                this.selectTable = row;
                this.tableData.splice(row.index, 1, {...this.selectTable});
            },
            async updateMainOrder(){
                try {
                    const MainOrderData = this.selectTable;
                    const res = await addMainOrder(MainOrderData)
                    if (res.code == 200) {
                        this.dialogFormVisible = false;
                        this.$message({
                            type: 'success',
                            message: '更新主单信息成功'
                        });
                        this.selectTable = {
                            id: null,
                            name: '',
                            details: '',
                            phone: '',
                            address: '',
                        }
                        this.getMainOrder();
                    } else {
                        this.$message({
                            type: 'error',
                            message: res.message
                        });
                    }
                } catch (err) {
                    console.log('更新主单信息失败', err);
                }
            },
            async handleDelete(index, row) {
                try {
                    this.mainOrderIds.push(row.id);
                    const res = await deleteMainOrders({
                        mainOrderIds: this.mainOrderIds
                    });
                    if (res.code == 200) {
                        this.$message({
                            type: 'success',
                            message: '删除主单成功'
                        });
                        this.tableData.splice(index, 1);
                    } else {
                        throw new Error(res.message)
                    }
                    this.mainOrderIds = [];
                } catch (err) {
                    this.$message({
                        type: 'error',
                        message: err.message
                    });
                    console.log('删除主单失败')
                }
            },
            async getMainOrder(){
                try {
                    const res = await listMainOrder({
                            pageNo: this.currentPage,
                            pageSize: this.limit,
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
                                orderDesc: item.orderDesc,
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

    .el-table {
        overflow: hidden;
        width: 100%;
        max-width: 100%;
        background-color: #fff;
        border: 1px solid #dfe6ec;
        font-size: 12px;
        color: #1f2d3d;
    }

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


