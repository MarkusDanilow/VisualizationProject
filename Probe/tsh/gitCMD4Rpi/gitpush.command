clear
cd ~/VisualizationProject/
git add .
echo "Enter commit-text"
read commitText
echo "Your INPUT:"
echo "$commitText"
git commit -a -m "$commitText"
git push origin master
echo "git-bash done!"