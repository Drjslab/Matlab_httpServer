function test=myans()



myans = 1+2;
a = magic(4);




fid = fopen('output.txt','w');

fprintf(fid,'%d',a);
fprintf(fid,'%d',myans);
fclose(fid);


end
