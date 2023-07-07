<template>
  <div>
    <el-container style="height: 500px; border: 1px solid #eee">
      <el-header>员工信息管理系统</el-header>
      <el-container>
        <el-aside width="230px" style="border: 1px solid #eee">
          <el-menu :default-openeds="['1', '3']">
            <el-submenu index="1">
              <template slot="title"
                ><i class="el-icon-message"></i>系统信息管理</template
              >
              <el-menu-item index="1-1">
                <router-link to="/dept"
                  ><el-link :underline="false">部门管理</el-link></router-link
                >
              </el-menu-item>
              <el-menu-item index="1-2">
                <router-link to="/emp"
                  ><el-link :underline="false">员工管理</el-link></router-link
                >
              </el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-main>
          <!--搜索表单-->
          <el-form :inline="true" :model="searchEmp" class="demo-form-inline">
            <el-form-item label="姓名">
              <el-input
                v-model="searchEmp.name"
                placeholder="请输入员工姓名"
              ></el-input>
            </el-form-item>
            <el-form-item label="性别">
              <el-select v-model="searchEmp.gender" placeholder="请选择">
                <el-option label="男" value="1"></el-option>
                <el-option label="女" value="2"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="入职时间">
              <el-date-picker
                v-model="birthday"
                unlink-panels
                clearable
                value-format="yyyy-MM-dd"
                type="daterange"
                placeholder="选择日期"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                style="width: 400px; margin-left: 20px"
              ></el-date-picker>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="onSubmit">查询</el-button>
              <el-button type="info" @click="clear">清空</el-button>
            </el-form-item>
          </el-form>

          <!-- 按钮 -->
          <el-row>
            <el-button type="danger" size="medium" @click="deleteByIds"
              >- 批量删除</el-button
            >
            <el-button
              type="primary"
              size="medium"
              @click="
                dialogVisible = true;
                emp = { image: '' };
              "
              >+ 新增员工</el-button
            >
          </el-row>
          <!--添加数据对话框表单-->
          <el-dialog
            ref="form"
            title="编辑员工"
            :visible.sync="dialogVisible"
            width="30%"
          >
            <el-form ref="form" :model="emp" label-width="80px" size="mini">
              <el-form-item label="工号">
                <el-input v-model="emp.number"></el-input>
              </el-form-item>
              <el-form-item label="员工姓名">
                <el-input v-model="emp.name"></el-input>
              </el-form-item>

              <el-form-item label="性别">
                <el-select
                  v-model="emp.gender"
                  placeholder="请选择"
                  style="width: 100%"
                >
                  <el-option
                    v-for="item in genderList"
                    :key="item.value"
                    :label="item.name"
                    :value="item.id"
                  />
                </el-select>
              </el-form-item>

              <el-form-item label="头像">
                <el-upload
                  class="avatar-uploader"
                  action="http://localhost:8080/upload"
                  name="image"
                  :show-file-list="false"
                  :on-success="handleAvatarSuccess"
                  :before-upload="beforeAvatarUpload"
                >
                  <img v-if="emp.image" :src="emp.image" class="avatar" />
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
              </el-form-item>

              <!-- <el-form-item label="职位">
                <el-select
                  v-model="emp.job"
                  placeholder="请选择"
                  value-key="emp.job"
                  style="width: 100%"
                >
                  <el-option
                    v-for="item in jobList"
                    :key="item.value"
                    :label="item.name"
                    :value="item.id"
                  />
                </el-select>
              </el-form-item> -->

              <el-form-item label="员工籍贯">
                <el-input v-model="emp.address"></el-input>
              </el-form-item>
              <el-form-item label="出生日期">
                <el-date-picker
                  v-model="emp.birthday"
                  clearable
                  type="date"
                  placeholder="选择日期"
                  size="small"
                  style="width: 100%"
                ></el-date-picker>
              </el-form-item>
              <el-form-item label="员工学历">
                <el-input v-model="emp.education"></el-input>
              </el-form-item>
              <el-form-item label="毕业院校">
                <el-input v-model="emp.school"></el-input>
              </el-form-item>
              <el-form-item label="员工专业">
                <el-input v-model="emp.major"></el-input>
              </el-form-item>
              <el-form-item label="归属部门">
                <el-select
                  v-model="emp.deptId"
                  placeholder="请选择"
                  style="width: 100%"
                >
                  <el-option
                    v-for="item in deptList"
                    :key="item.value"
                    :label="item.name"
                    :value="item.id"
                  />
                </el-select>
              </el-form-item>

              <el-form-item>
                <el-button type="primary" @click="add">提交</el-button>
                <el-button @click="dialogVisible = false">取消</el-button>
              </el-form-item>
            </el-form>
          </el-dialog>
          <br />
          <!--表格-->
          <template>
            <el-table
              :data="tableData"
              style="width: 100%"
              border
              @selection-change="handleSelectionChange"
            >
              <el-table-column
                type="selection"
                width="55"
                align="center"
              ></el-table-column>
              <el-table-column
                prop="number"
                label="工号"
                align="center"
              ></el-table-column>
              <el-table-column
                prop="name"
                label="姓名"
                align="center"
              ></el-table-column>
              <el-table-column prop="image" label="头像" align="center">
                <template slot-scope="{ row }">
                  <el-image
                    style="
                      width: auto;
                      height: 60px;
                      border: none;
                      cursor: pointer;
                    "
                    :src="row.image"
                  ></el-image>
                </template>
              </el-table-column>

              <el-table-column align="center" label="性别">
                <template slot-scope="scope">
                  <span style="margin-right: 10px">
                    {{ scope.row.gender == "1" ? "男" : "女" }}</span
                  >
                </template>
              </el-table-column>

              <!-- <el-table-column align="center" label="职位">
                <template slot-scope="scope">
                  <span style="margin-right: 10px" v-if="scope.row.job == 1"
                    >班主任</span
                  >
                  <span style="margin-right: 10px" v-if="scope.row.job == 2"
                    >讲师</span
                  >
                  <span style="margin-right: 10px" v-if="scope.row.job == 3"
                    >学工主管</span
                  >
                  <span style="margin-right: 10px" v-if="scope.row.job == 4"
                    >教研主管</span
                  >
                </template>
              </el-table-column> -->

              <el-table-column align="center" label="出生日期">
                <template slot-scope="scope">
                  {{ scope.row.birthday }}
                </template>
              </el-table-column>
              <el-table-column
                prop="address"
                label="籍贯"
                align="center"
              ></el-table-column>
              <el-table-column
                prop="education"
                label="学历"
                align="center"
              ></el-table-column>
              <el-table-column
                prop="school"
                label="毕业院校"
                align="center"
              ></el-table-column>
              <el-table-column
                prop="major"
                label="专业"
                align="center"
              ></el-table-column>
              <el-table-column align="center" label="最后操作时间">
                <template slot-scope="scope">
                  {{
                    scope.row.updateTime
                      ? scope.row.updateTime.replace("T", " ")
                      : ""
                  }}
                </template>
              </el-table-column>

              <el-table-column align="center" label="操作" width="150">
                <template slot-scope="scope">
                  <el-button
                    type="primary"
                    size="small"
                    @click="update(scope.row.id)"
                    >编辑</el-button
                  >
                  <!-- <br> -->
                  <el-button
                    type="danger"
                    size="small"
                    @click="deleteById(scope.row.id)"
                    >删除</el-button
                  >
                </template>
              </el-table-column>
            </el-table>
          </template>
          <br />
          <!-- 分页栏 -->
          <!--分页工具条-->
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :background="background"
            :current-page="currentPage"
            :page-sizes="[5, 10, 15, 20]"
            :page-size="5"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalCount"
          >
          </el-pagination>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
  <script>
export default {
  data() {
    return {
      formInline: {
        name: "",
        gender: "",
        date: [],
      },
      // uploadUrl:"",
      background: true,
      // 每页显示的条数
      pageSize: 5,
      // 总记录数
      totalCount: 100,
      // 当前页码
      currentPage: 1,
      // 添加数据对话框是否展示的标记
      dialogVisible: false,
      // 品牌模型数据
      searchEmp: {
        name: "",
        gender: "",
      },
      emp: {
        image: "",
      },
      deptList: [],
      genderList: [
        { id: 1, name: "男" },
        { id: 2, name: "女" },
      ],
      jobList: [
        {
          id: 1,
          name: "班主任",
        },
        {
          id: 2,
          name: "讲师",
        },
        {
          id: 3,
          name: "学工主管",
        },
        {
          id: 4,
          name: "教研主管",
        },
      ],
      beginTime: "",
      endTime: "",
      time: "",
      birthday: "",

      // 被选中的id数组
      selectedIds: [],
      // 复选框选中数据集合
      multipleSelection: [],
      // 表格数据
      tableData: [],

      // token: {token: localStorage.setItem(TokenKey,token)}
    };
  },
  methods: {
    page() {
      this.$http
        .get(
          `/emps?page=${this.currentPage}&pageSize=${this.pageSize}
          &name=${this.searchEmp.name}&gender=${this.searchEmp.gender}
          &begin=${this.beginTime}&end=${this.endTime}`
        )
        .then((res) => {
          this.totalCount = res.data.data.total;
          this.tableData = res.data.data.rows;
          // console.log(this.time[0]);
          // console.log(this.time[1]);
        });
    },
    // 复选框选中后执行的方法
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    //分页
    handleSizeChange(val) {
      // 重新设置每页显示的条数
      // console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.page();
    },

    handleCurrentChange(val) {
      // 重新设置当前页码
      this.currentPage = val;
      console.log(`当前页: ${val}`);
      this.page();
    },
    // 查询方法
    onSubmit() {
      this.currentPage = 1;
      this.page();
    },
    clear() {
      this.searchEmp = { name: "", gender: "" };
      (this.beginTime = ""), (this.endTime = "");
      this.entrydate = "";
      this.page();
    },
    deleteById(id) {
      this.$confirm("此操作将永久删除该员工, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$http.delete("/emps/" + id).then(() => {
            this.page();
          });
          this.$message({
            type: "success",
            message: "删除成功!",
          });
          // this.$router.go(0);
          // this.page()
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    deleteByIds() {
      if(this.multipleSelection.length==0){
        this.$message({
            type: "error",
            message: "未选择员工",
          });
      }else{
        this.$confirm("此操作将永久删除这些员工, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          //用户点击确认按钮
          //1. 创建id数组, 从 this.multipleSelection 获取即可
          for (let i = 0; i < this.multipleSelection.length; i++) {
            this.selectedIds[i] = this.multipleSelection[i].id;
          }
          this.$http.delete("/emps/" + this.selectedIds).then(() => {
            this.page();
          });
          // console.log(this.selectedIds);
          this.$message({
            type: "success",
            message: "删除成功!",
          });
          // this.$router.go(0);
          // this.page()
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
      }
      
    },
    add() {
      if (this.emp.id) {
        this.$http
          .put("/emps", {
            number: this.emp.number,
            name: this.emp.name,
            gender: this.emp.gender,
            image: this.emp.image,
            deptId: this.emp.deptId,
            birthday: this.emp.birthday,
            address:this.emp.address,
            education: this.emp.education,
            school:this.emp.school,
            major:this.emp.major,
            id: this.emp.id,
          })
          .then(() => {
            this.emp = { image: "" };
            this.page();
            this.dialogVisible = false;
          });
      } else {
        this.$http
          .post("/emps", {
            number: this.emp.number,
            name: this.emp.name,
            gender: this.emp.gender,
            image: this.emp.image,
            deptId: this.emp.deptId,
            birthday: this.emp.birthday,
            address:this.emp.address,
            education: this.emp.education,
            school:this.emp.school,
            major:this.emp.major,
            id: this.emp.id,
          })
          .then(() => {
            this.emp = { image: "" };
            this.page();
            this.dialogVisible = false;
          });
      }

      // console.log(this.emp.entrydate);
      // this.$router.go(0);
    },
    // upload() {
    //   console.log(this.uploadUrl)
    //   this.$https.post("/upload").then((res)=>{
    //     this.uploadUrl = res.data.data
    //   });

    // },
    //文件上传相关
    handleAvatarSuccess(res) {
      this.emp.image = res.data;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    update(id) {
      //1. 打开窗口
      this.dialogVisible = true;
      //2. 发送请求

      this.$http.get("/emps/" + id).then((result) => {
        if (result.data.code == 1) {
          this.emp = result.data.data;
          this.emp;
        }
      });
    },
  },
  mounted() {
    this.page();
    this.$http.get("/depts").then((result) => {
      this.deptList = result.data.data;
    });
  },
  watch: {
    birthday(val) {
      if (val && val.length >= 2) {
        this.beginTime = val[0];
        this.endTime = val[1];
      } else {
        this.beginTime = "";
        this.endTime = "";
      }
    },
  },
};
</script>
  <style>
.el-header {
  background-color: #899eaa;
  color: #fff;
  text-align: left;
  line-height: 60px;
  font-size: 40px;
}

.el-aside {
  background-color: #f4f6f9;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #fff;
  color: #333;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
}
.avatar {
  width: 100x;
  height: 100px;
  display: block;
}
</style>