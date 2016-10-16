In fact, neither head dump or comparison the heap usage can help in detecting livelocks.
It's obviously right because livelicks after "locking" it's "gears" become literally a endless loops.
Only thread dump shows that fact that crucial threads are in state "TIMED_WAITING (sleeping)",so it isn't good.
Also, good logging coverage should help in detecting livelocks.