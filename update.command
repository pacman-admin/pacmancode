echo "Updating Pac-Man..."
BASEDIR=$(dirname "$0")
echo "$BASEDIR"
cd "$BASEDIR/pac_man.app/Contents/Java"
if test -f "new.jar"; then
    echo "File exists."
    mv pac_man_9.jar old.jar
    rm old.jar
    echo "Successfully removed old version"
    mv new.jar pac_man_9.jar
    read -p "Successfully updated Pac-Man. Press any key to exit.Pac-Man"
else
    echo ""
    echo ""
    echo ""
    echo "New version was not found!"
    echo ""
    echo ""
    echo ""
fi
