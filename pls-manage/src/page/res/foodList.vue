<template>
    <div class="fillcontain">
        <head-top></head-top>
        <div class="table_container">
            <el-form :model="queryForm" label-width="110px" class="form">
                <el-row class="category_select">
                    <el-form-item>
                        <el-input v-model="queryForm.name" placeholder="商品名称"></el-input>
                        <el-select v-model="queryForm.categorySelect" placeholder="请选择食品品种" style="width:100%;">
                            <el-option
                                v-for="item in queryForm.categoryList"
                                :key="item.index"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-row>
            </el-form>
            <div style="margin-bottom: 15px">
                <el-button @click="handleAdd" type="primary">新增</el-button>
            </div>
            <el-table
                :data="tableData"
                @expand='expand'
                :expand-row-keys='expendRow'
                :row-key="row => row.index"
                style="width: 100%">
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
                    label="菜品名称"
                    prop="name">
                </el-table-column>
                <el-table-column
                    label="菜品种类"
                    prop="speciesName">
                </el-table-column>
                <el-table-column
                    label="菜品详情"
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
                    :page-size="20"
                    layout="total, prev, pager, next"
                    :total="count">
                </el-pagination>
            </div>
            <el-dialog title="修改菜品信息" v-model="dialogFormVisible">
                <el-form :model="selectTable">
                    <el-form-item label="菜品名称" label-width="100px">
                        <el-input v-model="selectTable.name" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="菜品详情" label-width="100px">
                        <el-input v-model="selectTable.details"></el-input>
                    </el-form-item>
                    <el-row class="category_select">
                    <el-form-item label="食品价格" label-width="100px">
                        <el-input placeholder="食品价格参考：5.00" v-model="selectTable.price">
                            <el-select slot="append" v-model="selectTable.unitId"  style="width: 100px;">
                                <el-option
                                    v-for="item in unitList"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </el-input>
                    </el-form-item>
                    </el-row>
                    <el-form-item label="菜品分类" label-width="100px">
                        <el-select v-model="selectIndex" :placeholder="selectMenu.label" @change="handleSelect">
                            <el-option
                                v-for="item in menuOptions"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="菜品图片" label-width="100px">
                        <el-upload
                            class="avatar-uploader"
                            :action="baseUrl + '/v1/addimg/food'"
                            :show-file-list="false"
                            :on-success="handleServiceAvatarScucess"
                            :before-upload="beforeAvatarUpload">
                            <img v-if="selectTable.image_path" :src="baseUrl + selectTable.image_path" class="avatar">
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
    import {getFoods, getSpecies, updateFood, deleteFood, getResturantDetail, getMenuById} from '@/api/getData'
    export default {
        data(){
            return {
                baseUrl,
                offset: 1,
                limit: 20,
                count: 0,
                tableData: [],
                currentPage: 1,
                selectTable: {},
                dialogFormVisible: false,
                menuOptions: [],
                selectMenu: {},
                selectIndex: null,
                name: null,
                speciesId: null,
                expendRow: [],
                queryForm:{},
                unitList:[{
                    value: '1',
                    label: '元/斤'
                }, {
                    value: '2',
                    label: '元/瓶'
                },{
                    value: '3',
                    label: '元/袋'
                },{
                    value: '4',
                    label: '元/只'
                },{
                    value: '5',
                    label: '元/个'
                }],
            }
        },
        created(){
            this.initData();
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
                    menu.forEach((item, index) => {
                        this.menuOptions.push({
                            label: item.name,
                            value: item.id,
                            index: index
                        })
                    })
                } catch (err) {
                    console.log('获取菜品种类失败', err);
                }
            },
            async getFoods(){
                const Foods = await getFoods({
                    offset: this.offset,
                    limit: this.limit,
                    speciesId: this.speciesId,
                    name: this.name
                });
                if (Foods.code == 200) {
                    this.count = Foods.data.total;
                    this.tableData = [];
                    Foods.data.list.forEach((item, index) => {
                        const tableData = {};
                        tableData.name = item.name;
                        tableData.speciesId = item.speciesId;
                        tableData.speciesName = item.speciesName;
                        tableData.details = item.details;
                        tableData.imgLink = baseUrl + item.imgLink;
                        tableData.index = index;
                        this.tableData.push(tableData);
                    })
                } else {
                    throw new Error(Foods.message);
                }
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
                this.offset = (val - 1) * this.limit;
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
            handleAdd(){
                console.log(111);
                this.$router.push('/addGoods');
            },
            handleEdit(row) {
                this.getSelectItemData(row, 'edit');
                this.dialogFormVisible = true;
            },
            async getSelectItemData(row, type){
                const restaurant = await getResturantDetail(row.restaurant_id);
                const category = await getMenuById(row.category_id)
                this.selectTable = {
                    ...row, ...{
                        restaurant_name: restaurant.name,
                        restaurant_address: restaurant.address,
                        category_name: category.name
                    }
                };

                this.selectMenu = {label: category.name, value: row.category_id}
                this.tableData.splice(row.index, 1, {...this.selectTable});
                this.$nextTick(() => {
                    this.expendRow.push(row.index);
                })
                if (type == 'edit') {
                    this.getMenu();
                }
            },
            handleSelect(index){
                this.selectIndex = index;
                this.selectMenu = this.menuOptions[index];
            },
            async handleDelete(index, row) {
                try {
                    const res = await deleteFood(row.item_id);
                    if (res.status == 1) {
                        this.$message({
                            type: 'success',
                            message: '删除菜品成功'
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
                    console.log('删除菜品失败')
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
                this.dialogFormVisible = false;
                try {
                    const subData = {new_category_id: this.selectMenu.value};
                    const postData = {...this.selectTable, ...subData};
                    const res = await updateFood(postData)
                    if (res.status == 1) {
                        this.$message({
                            type: 'success',
                            message: '更新菜品信息成功'
                        });
                        this.getFoods();
                    } else {
                        this.$message({
                            type: 'error',
                            message: res.message
                        });
                    }
                } catch (err) {
                    console.log('更新餐馆信息失败', err);
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

    .category_select{
        width: 350px;
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
