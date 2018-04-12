<template>
    <div class="fillcontain">
        <head-top></head-top>
        <div class="table_container">
            <el-table
                :data="tableData"
                style="width: 100%">
                <el-table-column
                    type="index"
                    label="序号"
                    width="100">
                </el-table-column>
                <el-table-column
                    property="name"
                    label="分类名称">
                </el-table-column>
                <el-table-column
                    property="remarks"
                    label="分类备注">
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
                    :page-size="20"
                    layout="total, prev, pager, next"
                    :total="count">
                </el-pagination>
            </div>
            <el-dialog :title="modalTitle" v-model="dialogFormVisible">
                <el-form :model="selectTable">
                    <el-form-item label="分类名称" label-width="100px">
                        <el-input v-model="selectTable.name" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="分类备注" label-width="100px">
                        <el-input v-model="selectTable.remarks"></el-input>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="updateSpecies">确 定</el-button>
                </div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import headTop from '../../components/headTop'
    import {getSpeciesList,deleteSpecies,addSpecies} from '@/api/getData'
    export default {
        data(){
            return {
                tableData: [],
                currentRow: null,
                offset: 1,
                limit: 20,
                count: 0,
                currentPage: 1,
                dialogFormVisible: false,
                name:null,
                modalTitle:'',
                selectTable:{}
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
                    this.getSpecies();
                } catch (err) {
                    console.log('获取数据失败', err);
                }
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.offset = (val - 1) * this.limit;
                this.getSpecies()
            },
            handleEdit(row,type){
                this.getSelectItemData(row);
                this.dialogFormVisible = true;
                if(type == 'edit'){
                    this.modalTitle = '编辑食品分类';
                }else {
                    this.modalTitle = '新增食品分类';
                }
            },
            updateSpecies(){

            },
            async getSelectItemData(row){
                this.selectTable = row;
            },
            async handleDelete(index, row) {
                try {
                    this.foodIds.push(row.id);
                    console.log(this.foodIds);
                    const res = await deleteFood({
                        foodIds: this.foodIds
                    });
                    if (res.code == 200) {
                        this.$message({
                            type: 'success',
                            message: '删除食品成功'
                        });
                        this.tableData.splice(index, 1);
                    } else {
                        throw new Error(res.message)
                    }
                } catch (err) {
                    this.$message({
                        type: 'error',
                        message: err.message
                    });
                    console.log('删除食品失败')
                }
            },
            async getSpecies(){
                try {
                    const res = await getSpeciesList({pageNo: this.offset, pageSize: this.limit,name:this.name});
                    if (res.code == 200) {
                        this.count = res.data.total;
                        this.tableData = [];
                        res.data.list.forEach(item => {
                            const tableItem = {
                                name: item.name,
                                remarks: item.remarks
                            }
                            this.tableData.push(tableItem);
                        })
                        console.log(this.tableData);
                    } else {
                        throw new Error(res.message)
                    }
                } catch (err) {
                    console.log('获取数据失败', err);
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
</style>


