<template>
    <div class="fillcontain">
        <head-top></head-top>
        <div class="table_container">
            <div class="demo-input-size">
                <el-row class="category_select2">
                    <el-input v-model="queryForm.name" placeholder="食品名称" style="width:200px;"></el-input>
                    <el-select v-model="queryForm.speciesId" placeholder="请选择食品分类" style="width:200px;">
                        <el-option
                        v-for="item in menuSelectOptions"
                        :key="item.value + 'B'"
                        :label="item.label"
                        :value="item.value">
                        </el-option>
                    </el-select>
                    <el-button @click="handleQuery" type="primary">查询</el-button>
                </el-row>
            </div>
            <div style="margin-bottom: 10px">
                <el-button @click="handleAdd" type="primary">新增</el-button>
                <el-button @click="handleBatchDel" type="primary">批量删除</el-button>
            </div>
            <el-table
                :data="tableData"
                @expand='expand'
                :expand-row-keys='expendRow'
                :row-key="row => row.index"
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
                <el-table-column
                    label="食品详情"
                    prop="details">
                </el-table-column>
                <el-table-column label="操作" width="160">
                    <template scope="scope">
                        <el-button
                            size="small"
                            @click="handleEdit(scope.row)">编辑
                        </el-button>
                        <el-button
                            size="small"
                            type="danger"
                            @click="handleDelete(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
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
            <el-dialog title="修改食品信息" v-model="dialogFormVisible">
                <el-form :model="selectTable">
                    <el-form-item label="食品名称" label-width="100px">
                        <el-input v-model="selectTable.name" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="食品详情" label-width="100px">
                        <el-input v-model="selectTable.details"></el-input>
                    </el-form-item>
                    <el-form-item label="食品价格" label-width="100px">
                        <el-input placeholder="食品价格参考：5.00" v-model="selectTable.price">
                            <el-select slot="append" v-model="selectTable.unitId" :placeholder="selectUnitMenu.label" placeholder="请选择单位"  style="width: 120px;">
                                <el-option
                                    v-for="item in unitList"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </el-input>
                    </el-form-item>
                    <el-form-item label="食品分类" label-width="100px">
                        <el-select v-model="selectTable.speciesId" :placeholder="selectMenu.label" placeholder="请选择食品分类"  style="width: 100%;">
                            <el-option
                                v-for="item in menuOptions"
                                :key="item.value + 'A'"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="食品图片" label-width="100px">
                        <el-upload
                            class="avatar-uploader"
                            :action="baseUrl + '/api/upload/img'"
                            :show-file-list="false"
                            :on-success="handleServiceAvatarScucess"
                            :before-upload="beforeAvatarUpload">
                            <img v-if="selectTable.imgLink" :src="selectTable.imgLink" class="avatar">
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="updateFood">确 定</el-button>
                </div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import headTop from '../../components/headTop'
    import {baseUrl} from '@/config/env'
    import {getFoods, getSpecies, addFood, deleteFood, getMenuById} from '@/api/getData'
    export default {
        data(){
            return {
                baseUrl,
                limit: 10,
                count: 0,
                tableData: [],
                currentPage: 1,
                selectTable: {},
                dialogFormVisible: false,
                menuSelectOptions:[],
                menuOptions: [],
                selectMenu: {},
                selectUnitMenu:{},
                selectIndex: null,
                selectItems:[],
                name: null,
                speciesId: null,
                expendRow: [],
                foodIds:[],
                queryForm:{
                    speciesId:'',
                    name:''
                },
                unitList:[{
                    value: 1,
                    label: '元/斤'
                }, {
                    value: 2,
                    label: '元/瓶'
                },{
                    value: 3,
                    label: '元/袋'
                },{
                    value: 4,
                    label: '元/只'
                },{
                    value: 5,
                    label: '元/个'
                }],
                multipleSelection:[]
            }
        },
        created(){
            this.initData();
            this.getMenu();
        },
        computed: {},
        components: {
            headTop,
        },
        methods: {
            async initData(){
                try {
                    this.getFoods();
                } catch (err) {
                    console.log('获取数据失败', err);
                }
            },
            async getMenu(){
                this.menuOptions = [];
                try {
                    const menu = await getSpecies();
                    if(menu.code = 200){
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
                    }else {
                        console.log(menu.message);
                    }
                } catch (err) {
                    console.log('获取食品分类失败', err);
                }
            },
            async getFoods(){
                const Foods = await getFoods({
                    pageNo: this.currentPage,
                    pageSize: this.limit,
                    speciesId: this.queryForm.speciesId,
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
                        tableData.imgLink = baseUrl + item.imgLink;
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
            handleQuery(){
                this.initData();
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            tableRowClassName(row, index) {
                if (index === 1) {
                    return 'info-row';
                } else if (index === 3) {
                    return 'positive-row';
                }
                return '';
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.getFoods()
            },
            expand(row, status){
                if (status) {
                    this.getSelectItemData(row)
                } else {
                    const index = this.expendRow.indexOf(row.index);
                    this.expendRow.splice(index, 1)
                }
            },
            async handleBatchDel(){
                try {
                    if (this.multipleSelection.length <= 0){
                        this.$message({
                            type: 'warning',
                            message: '请选择食品数据！'
                        });
                        return false;
                    }
                    this.multipleSelection.forEach((item, index) => {
                        this.foodIds.push(item.id);
                    });
                    const res = await deleteFood({
                        foodIds: this.foodIds
                    });
                    if (res.code == 200) {
                        this.$message({
                            type: 'success',
                            message: '删除食品成功'
                        });
                        this.initData();
                    } else {
                        throw new Error(res.message)
                    }
                    this.multipleSelection = [];
                    this.foodIds = [];
                } catch (err) {
                    this.$message({
                        type: 'error',
                        message: err.message
                    });
                    console.log('删除食品失败')
                }
            },
            handleAdd(){
                this.$router.push('/addGoods');
            },
            handleEdit(row) {
                console.log("获取菜单数据");
                this.getSelectItemData(row);
                this.dialogFormVisible = true;
            },
            async getSelectItemData(row){
                this.selectTable = row;
                this.selectMenu = {label: row.speciesName, value: row.speciesId}
                this.selectUnitMenu = this.unitList[row.unitId - 1];
                this.tableData.splice(row.index, 1, {...this.selectTable});
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
                    this.foodIds = [];
                } catch (err) {
                    this.$message({
                        type: 'error',
                        message: err.message
                    });
                    console.log('删除食品失败')
                }
            },
            handleServiceAvatarScucess(res, file) {
                if (res.code == 200) {
                    this.selectTable.imgLink = baseUrl + res.imgLink;
                } else {
                    this.$message.error('上传图片失败！');
                }
            },
            beforeAvatarUpload(file) {
                const isRightType = (file.type === 'image/jpeg') || (file.type === 'image/png');
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isRightType) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isRightType && isLt2M;
            },
            async updateFood(){
                try {
                    const foodData = this.selectTable;
                    const url = foodData.imgLink.replace(this.baseUrl,'');
                    foodData.imgLink = url;
                    const res = await addFood(foodData)
                    if (res.code == 200) {
                        this.dialogFormVisible = false;
                        this.$message({
                            type: 'success',
                            message: '更新食品信息成功'
                        });
                        this.getFoods();
                    } else {
                        foodData.imgLink = this.baseUrl + foodData.imgLink;
                        this.$message({
                            type: 'error',
                            message: res.message
                        });
                    }
                } catch (err) {
                    console.log('更新食品信息失败', err);
                }
            },
        },
    }
</script>

<style lang="less">
    @import '../../style/mixin';

    .demo-table-expand {
        font-size: 0;
    }

    .demo-table-expand label {
        width: 90px;
        color: #99a9bf;
    }

    .demo-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }

    .table_container {
        padding: 20px;
    }

    .Pagination {
        display: flex;
        justify-content: flex-start;
        margin-top: 8px;
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

    .category_select2{
        width: 600px;
        border: 1px solid #eaeefb;
        padding: 10px 30px 10px 10px;
        border-top-right-radius: 6px;
        border-top-left-radius: 6px;
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
</style>
