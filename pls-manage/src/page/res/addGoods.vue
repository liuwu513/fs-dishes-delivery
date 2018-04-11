<template>
    <div>
     	<head-top></head-top>
        <el-row style="margin-top: 20px;">
  			<el-col :span="14" :offset="4">
  				<header class="form_header">选择食品种类</header>
	  			<el-form :model="categoryForm" :rules="categoryRules" ref="categoryForm" label-width="110px" class="form">
		  			<el-row class="category_select">
		  				<el-form-item label="食品种类">
			  				<el-select v-model="categoryForm.categorySelect" :placeholder="selectValue.label" style="width:100%;">
							    <el-option
							      	v-for="item in categoryForm.categoryList"
							      	:key="item.index"
							      	:label="item.label"
							      	:value="item.value">
							    </el-option>
						  	</el-select>
						</el-form-item>
					</el-row>
					<el-row class="add_category_row" :class="showAddCategory? 'showEdit': ''">
						<div class="add_category">
							<el-form-item label="食品种类" prop="name">
								<el-input v-model="categoryForm.name"></el-input>
							</el-form-item>
							<el-form-item label="种类描述" prop="remarks">
								<el-input v-model="categoryForm.remarks"></el-input>
							</el-form-item>
							<el-form-item>
								<el-button type="primary" @click="submitCategoryForm('categoryForm')">提交</el-button>
							</el-form-item>
						</div>
					</el-row>
					<div class="add_category_button" @click="addCategoryFun">
						<i class="el-icon-caret-top edit_icon" v-if="showAddCategory"></i>
						<i class="el-icon-caret-bottom edit_icon" v-else slot="icon"></i>
						<span>添加食品种类</span>
					</div>
	  			</el-form>
	  			<header class="form_header">添加食品</header>
	  			<el-form :model="foodForm" :rules="foodRules" ref="foodForm" label-width="110px" class="form food_form">
	  				<el-form-item label="食品名称" prop="name">
						<el-input v-model="foodForm.name"></el-input>
					</el-form-item>
                    <el-form-item label="食品价格" prop="price">
                        <el-input placeholder="食品价格参考：5.00" v-model="foodForm.price">
                            <el-select slot="append" v-model="foodForm.unitId"  style="width: 100px;">
                                <el-option
                                    v-for="item in categoryForm.unitList"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </el-input>
                    </el-form-item>
					<el-form-item label="食品详情" prop="details">
						<el-input v-model="foodForm.details"></el-input>
					</el-form-item>
					<el-form-item label="上传食品图片">
						<el-upload
						  class="avatar-uploader"
						  :action="baseUrl + '/api/upload/img'"
						  :show-file-list="false"
						  :on-success="uploadImg"
						  :before-upload="beforeImgUpload" name="imgFile">
						  <img v-if="foodForm.imgLink" :src="baseUrl + foodForm.imgLink" class="avatar">
						  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
						</el-upload>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="addFood('foodForm')">确认添加食品</el-button>
					</el-form-item>
	  			</el-form>
  			</el-col>
  		</el-row>
    </div>
</template>

<script>
 	import headTop from '@/components/headTop'
    import {getSpecies, addSpecies, addFood} from '@/api/getData'
    import {baseUrl} from '@/config/env'
    export default {
    	data(){
    		return {
    			baseUrl,
    			categoryForm: {
    				categoryList: [],
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
                    unitValue:'',
                    categorySelect: '',
                    unitSelect: {
                        value: '1',
                        label: '元/斤'
                    },
    				name: '',
    				remarks: '',
    			},
                categoryRules:{
                    speciesId:[
                        { required: true, message: '请选择食品种类', trigger: 'blur' },
                    ]
                },
    			foodForm: {
    				name: '',
    				details: '',
    				imgLink: '',
    				speciesId: '',
    				feature: '',
    				price: null,
                    unitId: null
    			},
    			foodRules: {

    				name: [
						{ required: true, message: '请输入食品名称', trigger: 'blur' },
					],
                    price:[
                        { required: true, message: '请输入食品价格', trigger: 'blur' },
                    ]
    			},
    			showAddCategory: false,
    			dialogFormVisible: false
    		}
    	},
    	components: {
    		headTop,
    	},
    	created(){
    		this.initData();
    	},
    	computed: {
    		selectValue: function (){
    			return this.categoryForm.categoryList[this.categoryForm.categorySelect]||{}
    		}
    	},
    	methods: {
    		async initData(){
    			try{
    				const result = await getSpecies();
                    console.log(result.data);
	    			if (result.code == 200) {
	    				result.data.map((item, index) => {
	    				    item.index = index;
	    					item.value = item.id;
	    					item.label = item.name;
	    				})
	    				this.categoryForm.categoryList = result.data;
                        console.log(this.categoryForm.categoryList);
	    			}else{
	    				console.log(result)
	    			}
    			}catch(err){
    				console.log(err)
    			}
    		},
		    addCategoryFun(){
		    	this.showAddCategory = !this.showAddCategory;
		    },
		    submitCategoryForm(categoryForm) {
				this.$refs[categoryForm].validate(async (valid) => {
					if (valid) {
						const params = {
							name: this.categoryForm.name,
                            remarks: this.categoryForm.remarks
						}
						try{
							const result = await addSpecies(params);
							if (result.status == 1) {
								this.initData();
								this.categoryForm.name = '';
								this.categoryForm.remarks = '';
								this.showAddCategory = false;
								this.$message({
					            	type: 'success',
					            	message: '添加成功'
					          	});
							}
						}catch(err){
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
			},
			uploadImg(res, file) {
				if (res.code == 200) {
					this.foodForm.imgLink = res.data;
				}else{
					this.$message.error('上传图片失败！');
				}
			},
			beforeImgUpload(file) {
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
			tableRowClassName(row, index) {
		        if (index === 1) {
		        	return 'info-row';
		        } else if (index === 3) {
		        	return 'positive-row';
		        }
		        return '';
		    },
		    addFood(foodForm){
		    	this.$refs[foodForm].validate(async (valid) => {
					if (valid) {
						const params = {
							...this.foodForm,
							speciesId: this.categoryForm.categorySelect
						}
						try{
							const result = await addFood(params);
							if (result.status == 1) {
								console.log(result)
								this.$message({
					            	type: 'success',
					            	message: '添加成功'
					          	});
					          	this.foodForm = {
				    				name: '',
				    				details: '',
				    				imgLink: '',
                                    speciesId: '',
                                    feature: '',
				    				price: 0.00,
				    			}
                                this.$router.push('/foodList');
							}else{
								this.$message({
					            	type: 'error',
					            	message: result.message
					          	});
							}
						}catch(err){
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
		min-width: 400px;
		margin-bottom: 30px;
		&:hover{
			box-shadow: 0 0 8px 0 rgba(232,237,250,.6), 0 2px 4px 0 rgba(232,237,250,.5);
			border-radius: 6px;
			transition: all 400ms;
		}
	}
	.food_form{
		border: 1px solid #eaeefb;
		padding: 10px 10px 0;
	}
	.form_header{
		text-align: center;
		margin-bottom: 10px;
	}
	.category_select{
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
	.showEdit{
		height: 185px;
	}
	.add_category{
		background: #f9fafc;
		padding: 10px 30px 0px 10px;
		border: 1px solid #eaeefb;
		border-top: none;
	}
	.add_category_button{
		text-align: center;
		line-height: 40px;
		border-bottom-right-radius: 6px;
		border-bottom-left-radius: 6px;
		border: 1px solid #eaeefb;
		border-top: none;
		transition: all 400ms;
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
