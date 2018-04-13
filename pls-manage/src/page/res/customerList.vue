<template>
    <div class="fillcontain">
        <head-top></head-top>
        <div class="table_container">
            <div class="demo-input-size">
                <el-row class="category_select">
                    <el-input v-model="queryForm.name" placeholder="客户名称/手机号码" style="width:200px;"></el-input>
                    <el-button @click="handleQuery" type="primary">查询</el-button>
                </el-row>
            </div>
            <div style="margin-bottom: 10px">
                <el-button @click="handleAdd" type="primary">新增</el-button>
                <el-button @click="handleBatchDel" type="primary">批量删除</el-button>
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
                    type="index"
                    label="序号"
                    width="100">
                </el-table-column>
                <el-table-column
                    property="name"
                    label="客户名称">
                </el-table-column>
                <el-table-column
                    property="phone"
                    label="手机号码">
                </el-table-column>
                <el-table-column
                    property="address"
                    label="客户地址">
                </el-table-column>
                <el-table-column
                    property="remarks"
                    label="备注">
                </el-table-column>
                <el-table-column label="操作" width="160">
                    <template scope="scope">
                        <el-button
                            size="small"
                            @click="handleEdit(scope.row,'edit')">编辑
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
                    <el-form-item label="客户名称" label-width="100px">
                        <el-input v-model="selectTable.name" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="手机号码" label-width="100px">
                        <el-input v-model="selectTable.phone"></el-input>
                    </el-form-item>
                    <el-form-item label="客户地址" label-width="100px">
                        <el-input v-model="selectTable.address"></el-input>
                    </el-form-item>
                    <el-form-item label="备注" label-width="100px">
                        <el-input v-model="selectTable.remarks"></el-input>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="updateCustomer">确 定</el-button>
                </div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import headTop from '../../components/headTop'
    import {listCustomers,getCustomers,addCustomer,deleteCustomers} from '@/api/getData'
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
                    name:''
                },
                modalTitle:'',
                selectTable:{},
                customerIds:[],
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
            async initData(){
                try {
                    this.getCustomer();
                } catch (err) {
                    console.log('获取数据失败', err);
                }
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.getCustomer()
            },
            handleEdit(row,type){
                this.getSelectItemData(row);
                this.dialogFormVisible = true;
                if(type == 'edit'){
                    this.modalTitle = '编辑客户信息';
                }else {
                    this.modalTitle = '新增客户信息';
                }
            },
            handleQuery(){
                this.initData();
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            handleAdd(){
                this.modalTitle = '新增客户信息';
                this.dialogFormVisible = true;
            },
            async handleBatchDel(){
                try {
                    if (this.multipleSelection.length <= 0){
                        this.$message({
                            type: 'warning',
                            message: '请选择客户数据！'
                        });
                        return false;
                    }
                    this.multipleSelection.forEach((item, index) => {
                        this.customerIds.push(item.id);
                    });
                    const res = await deleteCustomers({
                        customerIds: this.customerIds
                    });
                    if (res.code == 200) {
                        this.$message({
                            type: 'success',
                            message: '删除客户成功'
                        });
                        this.initData();
                    } else {
                        throw new Error(res.message)
                    }
                    this.multipleSelection = [];
                    this.customerIds = [];
                } catch (err) {
                    this.$message({
                        type: 'error',
                        message: err.message
                    });
                    console.log('删除客户失败')
                }
            },
            async getSelectItemData(row){
                this.selectTable = row;
                this.tableData.splice(row.index, 1, {...this.selectTable});
            },
            async updateCustomer(){
                try {
                    const customerData = this.selectTable;
                    const res = await addCustomer(customerData)
                    if (res.code == 200) {
                        this.dialogFormVisible = false;
                        this.$message({
                            type: 'success',
                            message: '更新客户信息成功'
                        });
                        this.selectTable = {
                            id: null,
                            name: '',
                            remarks: '',
                            phone: '',
                            address: '',
                        }
                        this.getCustomer();
                    } else {
                        this.$message({
                            type: 'error',
                            message: res.message
                        });
                    }
                } catch (err) {
                    console.log('更新客户信息失败', err);
                }
            },
            async handleDelete(index, row) {
                try {
                    this.customerIds.push(row.id);
                    const res = await deleteCustomers({
                        customerIds: this.customerIds
                    });
                    if (res.code == 200) {
                        this.$message({
                            type: 'success',
                            message: '删除客户成功'
                        });
                        this.tableData.splice(index, 1);
                    } else {
                        throw new Error(res.message)
                    }
                    this.customerIds = [];
                } catch (err) {
                    this.$message({
                        type: 'error',
                        message: err.message
                    });
                    console.log('删除客户失败')
                }
            },
            async getCustomer(){
                try {
                    const res = await listCustomers({pageNo: this.currentPage, pageSize: this.limit,name:this.queryForm.name});
                    if (res.code == 200) {
                        this.count = res.data.total;
                        this.tableData = [];
                        res.data.list.forEach((item,index) => {
                            const tableItem = {
                                id: item.id,
                                name: item.name,
                                remarks: item.remarks,
                                phone: item.phone,
                                address: item.address,
                                index: index
                            }
                            this.tableData.push(tableItem);
                        })
                        console.log(this.tableData);
                    } else {
                        throw new Error(res.message)
                    }
                } catch (err) {
                    console.log('获取客户数据失败', err);
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
    .category_select{
        width: 350px;
        border: 1px solid #eaeefb;
        padding: 10px 30px 10px 10px;
        border-top-right-radius: 6px;
        border-top-left-radius: 6px;
    }
</style>


