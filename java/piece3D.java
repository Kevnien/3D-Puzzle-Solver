// Christian Barajas, Christian Cosby, Kevin Nguyen
// Group #15
// CS 560

//source code by Professor Bill Root, San Diego State University
//class/file is used as part of cs560project



public class piece3D {
	point3D[] cubes;
	int nCubes;
	char pieceID;
	bitmapNode possiblePositionsBitmaps;
	int xMin;
	int xMax;
	int xPan;
	int yMin;
	int yMax;
	int yPan;
	int zMin;
	int zMax;
	int zPan;
    int amountOfPositions;
	
	//-------------------------------------------------------------
	
	public piece3D(char ID, int size){
		nCubes = size;
		pieceID = ID;
		cubes = new point3D[size];
		for (int i=0; i<size; i++){
			cubes[i] = new point3D();
		}
		possiblePositionsBitmaps = null;
	}
	
	//----------------------------------------------------------------
	
	public void setCube(int whichCube, int ix, int iy, int iz){
		cubes[whichCube].x = ix;
		cubes[whichCube].y = iy;
		cubes[whichCube].z = iz;
	}
	
	//------------------------------------------------------------------
	
	public piece3D applyIsometry(int isometryNumber){
		piece3D p = new piece3D(pieceID, nCubes);
		
		for(int whichCube=0; whichCube<p.nCubes; whichCube++){
			int x = cubes[whichCube].x;
			int y = cubes[whichCube].y;
			int z = cubes[whichCube].z;
			
			switch (isometryNumber){
			case 1: p.cubes[whichCube].x = x;
			 		p.cubes[whichCube].y = y;
			 		p.cubes[whichCube].z = z;
			 		break;
			 		
			case 2: p.cubes[whichCube].x = y;
	 				p.cubes[whichCube].y = x;
	 				p.cubes[whichCube].z = z;
	 				break;
	 				
			case 3: p.cubes[whichCube].x = z;
					p.cubes[whichCube].y = x;
					p.cubes[whichCube].z = y;
					break;
			
			case 4: p.cubes[whichCube].x = -y;
					p.cubes[whichCube].y = x;
					p.cubes[whichCube].z = z;
					break;
					
			case 5: p.cubes[whichCube].x = -z;
					p.cubes[whichCube].y = y;
					p.cubes[whichCube].z = x;
					break;
					
			case 6: p.cubes[whichCube].x = -x;
					p.cubes[whichCube].y = z;
					p.cubes[whichCube].z = y;
					break;
					
			case 7: p.cubes[whichCube].x = -x;
					p.cubes[whichCube].y = -y;
					p.cubes[whichCube].z = z;
					break;
					
			case 8: p.cubes[whichCube].x = -y;
					p.cubes[whichCube].y = -z;
					p.cubes[whichCube].z = x;
					break;
					
			case 9: p.cubes[whichCube].x = -z;
					p.cubes[whichCube].y = -x;
					p.cubes[whichCube].z = y;
					break;
					
			case 10: p.cubes[whichCube].x = y;
					p.cubes[whichCube].y = -x;
					p.cubes[whichCube].z = z;
					break;
					
			case 11: p.cubes[whichCube].x = z;
					p.cubes[whichCube].y = -y;
					p.cubes[whichCube].z = x;
					break;
					
			case 12: p.cubes[whichCube].x = x;
					p.cubes[whichCube].y = -z;
					p.cubes[whichCube].z = y;
					break;
					
			case 13: p.cubes[whichCube].x = x;
					p.cubes[whichCube].y = z;
					p.cubes[whichCube].z = -y;
					break;
					
			case 14: p.cubes[whichCube].x = y;
					p.cubes[whichCube].y = x;
					p.cubes[whichCube].z = -z;
					break;
					
			case 15: p.cubes[whichCube].x = z;
					p.cubes[whichCube].y = y;
					p.cubes[whichCube].z = -x;
					break;
					
			case 16: p.cubes[whichCube].x = -z;
					p.cubes[whichCube].y = x;
					p.cubes[whichCube].z = -y;
					break;

            case 17: p.cubes[whichCube].x  = -x;
                     p.cubes[whichCube].y = y;
                     p.cubes[whichCube].z = -z;
                       break;
            case 18:    p.cubes[whichCube].x  = -y;
                        p.cubes[whichCube].y = z;
                        p.cubes[whichCube].z = -x;
                        break;
            case 19:    p.cubes[whichCube].x  = -x;
                        p.cubes[whichCube].y = -z;
                        p.cubes[whichCube].z = -y;
                        break;
            case 20:    p.cubes[whichCube].x  = -z;
                        p.cubes[whichCube].y = -x;
                        p.cubes[whichCube].z = -y;
                        break;
            case 21:    p.cubes[whichCube].x  = -y;
                        p.cubes[whichCube].y = -x;
                        p.cubes[whichCube].z = -z;
                        break;
            case 22:    p.cubes[whichCube].x  = z;
                        p.cubes[whichCube].y = -x;
                        p.cubes[whichCube].z = -y;
                        break;
            case 23:    p.cubes[whichCube].x  = y;
                        p.cubes[whichCube].y = -z;
                        p.cubes[whichCube].z = -x;
                        break;
            case 24:    p.cubes[whichCube].x  = x;
                        p.cubes[whichCube].y = -y;
                        p.cubes[whichCube].z = -z;
                        break;
            }
        }
    return p;
}

public void normalizePiece()
{
    xMin = 999;
    xMax = -999;
    yMin = 999;
    yMax = -999;
    zMin = 999;
    zMax = -999;

    for(int whichCube=0; whichCube<nCubes; whichCube++)
    {
        if(cubes[whichCube].x < xMin)
        {
            xMin = cubes[whichCube].x;
        }
        if(cubes[whichCube].y < yMin)
        {
            yMin = cubes[whichCube].y;
        }
        if(cubes[whichCube].z < zMin)
        {
            zMin = cubes[whichCube].z;
        }
        if(cubes[whichCube].x > xMax)
        {
            xMax = cubes[whichCube].x;
        }
        if(cubes[whichCube].y > yMax)
        {
            yMax = cubes[whichCube].y;
        }
        if(cubes[whichCube].z > zMax)
        {
            zMax = cubes[whichCube].z;
        }
    }

    for(int whichCube=0; whichCube<nCubes; whichCube++)
    {
        cubes[whichCube].x -= xMin;
        cubes[whichCube].y -= yMin;
        cubes[whichCube].z -= zMin;
    }

    xPan = cs560project.N - (xMax - xMin + 1);
    yPan = cs560project.N - (yMax - yMin + 1);
    zPan = cs560project.N - (zMax - zMin + 1);
}

public void findAllPossiblePositionsAsBitmaps()
{
    int positionIndex = 0;
    for(int whichIsometry=1; whichIsometry<=24; whichIsometry++)
    {
        piece3D q = applyIsometry(whichIsometry);
        q.normalizePiece();
        for(int dx=0; dx<=q.xPan; dx++)
        {
            for(int dy=0; dy<=q.yPan; dy++)
            {
                for( int dz=0; dz<=q.zPan; dz++)
                {
                    bitmapNode b = new bitmapNode();
                    for(int whichCube=0; whichCube<q.nCubes; whichCube++)
                    {
                        int x = q.cubes[whichCube].x + dx;
                        int y = q.cubes[whichCube].y + dy;
                        int z = q.cubes[whichCube].z + dz;
                        b.bitmap |= cs560project.mapTable[x][y][z];
                    }
                    if(possiblePositionsBitmaps == null)
                    {
                        possiblePositionsBitmaps = b;
                    }
                    else
                    {
                        bitmapNode p;
                        p = possiblePositionsBitmaps;
                        while( (p.link!=null) && (p.bitmap!=b.bitmap) )
                        {
                            p = p.link;
                        }
                        if( p.bitmap != b.bitmap )
                        {
                            positionIndex++;
                            //System.out.println("======= " + positionIndex);

                            p.link = b;
                            String m_string = Integer.toBinaryString(b.bitmap);
                            while(m_string.length()<27)
                            {
                                m_string = "0" + m_string;
                            }
                            System.out.println("integrated bitmap " + m_string);
                        }
                    }
                }
            }
        }
    }
    amountOfPositions = positionIndex;
    System.out.println("Amount of possible positions: " + amountOfPositions);
}

				
}// ends class

