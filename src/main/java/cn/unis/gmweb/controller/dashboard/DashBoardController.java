package cn.unis.gmweb.controller.dashboard;

import cn.unis.gmweb.pojo.DashBoard;
import cn.unis.gmweb.task.DashBoardTask;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class DashBoardController {

    @RequestMapping("/dashboard")
    public DashBoard getDashBoard() {
        if (DashBoardTask.dashBoardMemoryCache==null){
            return new DashBoardTask().InitDashBoard();
        }else {
            return DashBoardTask.dashBoardMemoryCache;
        }
    }

}
