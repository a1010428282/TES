package top.jach.tes.plugin.jach;

import top.jach.tes.core.domain.info.value.LongInfo;
import top.jach.tes.dev.app.InfoTool;
import top.jach.tes.dev.app.InputInfoProfiles;
import top.jach.tes.dev.app.TaskTool;
import top.jach.tes.plugin.tes.code.git.tree.GitTreeAction;
import top.jach.tes.plugin.tes.code.repo.Repo;
import top.jach.tes.core.domain.action.Action;
import top.jach.tes.core.domain.info.Info;
import top.jach.tes.core.domain.info.value.FileInfo;
import top.jach.tes.core.domain.info.value.StringInfo;
import top.jach.tes.plugin.tes.code.repo.ReposInfo;

import java.io.File;

public class GitTree {
    public static void main(String[] args) {
        // 创建不存在于数据库中的Info，一般是一些参数
        ReposInfo reposInfo = ReposInfo.createInfo();
        reposInfo.addRepo(new Repo().setName("tes"));
        Info repoName = StringInfo.createInfo(reposInfo.getRepos().get(0).getName());
        Info reposId = LongInfo.createInfo(reposInfo.getId());
        Info repoDir = FileInfo.createInfo(new File("./"));
        Info sha = StringInfo.createInfo("master");

        // 然后将这些info存入数据库
        InfoTool.saveInputInfos(repoDir, sha, reposInfo, reposId, repoName);

        // 创建一些已存在的InfoProfile
//        InfoProfile infoProfile = new InfoProfile(123l, ValueInfo.class);

        InputInfoProfiles infoProfileMap = InputInfoProfiles.InputInfoProfiles()
                .addInfoProfile(GitTreeAction.LOCAL_REPO_DIR, repoDir)
                .addInfoProfile(GitTreeAction.COMMIT_SHA, sha)
                .addInfoProfile(GitTreeAction.REPOS_ID, reposId)
                .addInfoProfile(GitTreeAction.REPO_NAME, repoName)
                ;

        Action action = new GitTreeAction();
        TaskTool.excuteActionAndSaveInfo(action, infoProfileMap);
    }
}