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
          
          <!-- 新增部门 -->

          <el-button type="primary" @click="dialogFormVisible = true"
            >+ 新增部门</el-button
          >

          <el-dialog title="保存部门" :visible.sync="dialogFormVisible">
            <el-form :model="dept">
              <el-form-item label="部门名称" :label-width="formLabelWidth">
                <el-input v-model="dept.name" autocomplete="off"></el-input>
              </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>

              <el-button
                type="primary"
                @click="add()"
                >确 定</el-button
              >
            </div>
          </el-dialog>
          <br /><br />
          <!-- 表格 -->
          <el-table :data="tableData" border
            >、
            <el-table-column
              type="index"
              label="ID"
              width="250"
            ></el-table-column>
            <el-table-column
              prop="name"
              label="名称"
              width="250"
            ></el-table-column>
            <el-table-column
              prop="updateTime"
              label="最后操作时间"
              width="250"
            ></el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="primary"
                  plain
                  @click="selectById(scope.row.id)"
                  >编辑</el-button
                >

                <el-button
                  size="mini"
                  type="danger"
                  plain
                  @click="deleteById(scope.row.id)"
                  >删除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
          
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
  
  <script>
export default {
  methods: {
    deleteById(id) {
      this.$confirm("确认删除?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$http.delete("/depts/" + id).then(() => {
            this.page();
          });
          this.$message({
            type: "success",
            message: "删除成功!",
          });
          this.$router.go(0);
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    add() {
      // 如果已经有值了就证明是要修改员工
      if(this.dept.id){
         this.$http.put("/depts",{
          name:this.dept.name,
          id:this.dept.id
        }).then(() => {
            this.page();
          });
        // console.log("你怒好")
      }else{
         this.$http
        .post("/depts", { name:this.dept.name }).then(() => {
            this.page();
          });
        // console.log("你好")
      }
     
        // .then((result) => {
        //   // 处理返回结果
        //   console.log(result.code)
        //   console.log(this.dept.name);
        // });
      this.dialogFormVisible = false;
      this.dept = {};
      // this.page()
      // this.$router.go(0);
    },
    selectById(id){
      this.dialogFormVisible = true;
      this.$http.get("/depts/"+id).then((result) => {
        this.dept = result.data.data;
        
      });
    },
    page(){
      this.$http.get("/depts").then((result) => {
      this.tableData = result.data.data;
    });
    }
  },
  data() {
    return {
      dept: {
        name: "",
        id:""
      },
      tableData: [],
      dialogFormVisible: false,
      form: {
        name: "",
        region: "",
        date1: "",
        date2: "",
        delivery: false,
        type: [],
        resource: "",
        desc: "",
      },
      formLabelWidth: "120px",
    };
  },
  created() {
    //发送异步请求获得数据
   this.page()
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
</style>