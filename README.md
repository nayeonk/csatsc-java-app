# CS@SC Web Registration System
A java application.
The CS@SC static website (WordPress) is NOT on this repository.

**Main** branch is the main branch, not the master branch. Keep **main** branch clean and up to date. This is the branch that is deployed to the production server.

## Development Workflow
1. Clone the repo.
2. Switch to `dev` branch.
3. Checkout a new branch from the `dev` branch. Use prefix `ft-` for new features, and `hotfix-` for fixes.
4. Develop on your local machine. How to set up your local environment is detailed in the Wiki of this repository.
5. Test on local machine. When everything looks good on local machine, get ready for deployment. Follow the steps listed out in https://github.com/nayeonk/csatsc-java-app/issues/1 to create a `.war` file.
6. SSH or SFTP into the dev server (test.csatsc.com).
7. Navigate to `/opt/tomcat/webapps` and upload the `.war` file. Wait a few seconds to fully deploy.
8. Test at test.csatsc.com. Double check everything. If all looks good, go back to GitHub and make a Pull Request to the `dev` branch.
9. When Pull Request is accepted, it's ready for deployment to production server! SSH or SFTP into the prod server (summercamp.usc.edu). 
10. Navigate to `/opt/tomcat/webapps` and upload the `.war` file. Wait a few seconds to fully deploy.
11. Test at summercamp.usc.edu. Double check everything. If all looks good, go back to GitHub and make a Pull Request to the `main` branch.
12. Review and accept Pull Request. Main branch is now up to date. Done!
