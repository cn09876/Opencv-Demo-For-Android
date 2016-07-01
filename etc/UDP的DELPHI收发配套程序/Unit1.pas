unit Unit1;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, ExtCtrls, IdUDPClient, IdBaseComponent, IdComponent, IdUDPBase,
  IdUDPServer, StdCtrls,idglobal,idsockethandle;

type
  TForm1 = class(TForm)
    udps: TIdUDPServer;
    Timer1: TTimer;
    Memo1: TMemo;
    Button1: TButton;
    procedure Timer1Timer(Sender: TObject);
    procedure FormCreate(Sender: TObject);
    procedure udpsStatus(ASender: TObject; const AStatus: TIdStatus;
      const AStatusText: String);
    procedure udpsUDPRead(Sender: TObject; AData: TBytes;
      ABinding: TIdSocketHandle);
    procedure Button1Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;
const
  sServerDEF='<server>I am server</server>';

implementation

{$R *.dfm}

procedure TForm1.Timer1Timer(Sender: TObject);
begin
  udps.Broadcast(sServerDEF,20421);
  //udps.Send('255.255.255.255',20420,sServerDEF);
end;

procedure TForm1.FormCreate(Sender: TObject);
begin
  udps.DefaultPort:=20420;
  udps.Active:=true;
  
end;

procedure TForm1.udpsStatus(ASender: TObject; const AStatus: TIdStatus;
  const AStatusText: String);
begin
  //
  memo1.Lines.Add(astatustext);
end;

procedure TForm1.udpsUDPRead(Sender: TObject; AData: TBytes;
  ABinding: TIdSocketHandle);
var
  s:string;
begin
  s:=idglobal.BytesToString(adata);
  memo1.Lines.Add('recv['+abinding.PeerIP+':'+inttostr(abinding.peerport)+']: '+s);
end;

procedure TForm1.Button1Click(Sender: TObject);
begin
  memo1.Clear;
end;

end.
