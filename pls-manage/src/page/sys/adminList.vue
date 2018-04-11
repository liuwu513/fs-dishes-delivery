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
                    property="username"
                    label="用户姓名"
                    width="220">
                </el-table-column>
                <el-table-column
                    property="email"
                    label="邮箱"
                    width="220">
                </el-table-column>
                <el-table-column
                    property="mobile"
                    label="手机号">
                </el-table-column>
                <el-table-column
                    prop="admin"
                    label="权限">
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
        </div>
    </div>
</template>

<script>
    import headTop from '../../components/headTop'
    import {adminList, adminCount} from '@/api/getData'
    export default {
        data(){
            return {
                tableData: [],
                currentRow: null,
                offset: 1,
                limit: 20,
                count: 0,
                currentPage: 1,
                username:null
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
                    this.getAdmin();
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
                this.getAdmin()
            },
            async getAdmin(){
                try {
                    const res = await adminList({pageNo: this.offset, pageSize: this.limit,username:this.username});
                    if (res.code == 200) {
                        this.count = res.data.totalCount;
                        this.tableData = [];
                        res.data.list.forEach(item => {
                            const tableItem = {
                                username: item.username,
                                email: item.email,
                                mobile: item.mobile
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


